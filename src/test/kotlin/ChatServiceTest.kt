import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    // arrange
    val Anna = User(1, "Anna")
    val Jack = User(2, "Jack")
    val Kate = User(3, "Kate")
    val Swen = User(4, "Swen")

    val message11 = Message(11, Anna.id, Jack.id, 11, true, "Hey there!")
    val message12 = Message(12, Anna.id, Jack.id, 11, true, "How are you?")

    val message21 = Message(21, Anna.id, Kate.id, 12, true, "Hey Kate!")
    val message22 = Message(22, Kate.id, Anna.id, 12, false, "Hi!")
    val message23 = Message(23, Kate.id, Anna.id, 12, true, "Hi again!")

    val message31 = Message(31, Jack.id, Kate.id, 13, true, "Hi!")
    val message32 = Message(32, Jack.id, Kate.id, 13, false, "Hello")

    val message41 = Message(41, Swen.id, Jack.id, 14, false, "Hello")

    init {
        ChatService.sendMessage(message11)
        ChatService.sendMessage(message12)
        ChatService.sendMessage(message21)
        ChatService.sendMessage(message22)
        ChatService.sendMessage(message23)
        ChatService.sendMessage(message31)
        ChatService.sendMessage(message32)
        ChatService.sendMessage(message41)
    }

    @Test
    fun getUnreadChatsCount() {
        // act
        val result = ChatService.getUnreadChatsCount()

        // assert
        assertTrue("Failed to perform request", result == 3)
    }

    @Test
    fun getChats() {
        // act
        val result = ChatService.getChats(Kate.id)

        // assert
        assertTrue("Failed to perform request", result.size == 2)
    }

    @Test
    fun getMessages() {
        // arrange
        val chatId = message21.chatId
        val lastMessage = message21.id
        val countMessages = 3

        // act
        val result = ChatService.getMessages(chatId, lastMessage, countMessages)

        // assert
        assertTrue("Failed to perform request", result.size == countMessages)
        assertTrue("Failed to perform request", result.all { it.chatId == chatId })
    }

    @Test
    fun sendMessage() {
        // arrange
        val chat = ChatService.getChatById(message11.chatId) ?: error("Chat not found")

        // act
        val result = ChatService.sendMessage(message11)

        // assert
        assertTrue("Message is not existing", chat.messages.contains(message11))
        assertTrue("Chat is not existing", ChatService.chats.any { it.id == message11.chatId })
        assertTrue("This message has a duplicate", chat.messages.count { it.id == message11.id } == 1 )
    }

    @Test
    fun deleteMessage() {
        // arrange
        val chat = ChatService.getChatById(message41.chatId) ?: error("Chat not found")

        // act
        val result = ChatService.deleteMessage(message41)

        // assert
        assertTrue("Failed to delete message", result)
        assertTrue("Deleted message ID is detected", chat.messages.none { it.id == message41.id })
        assertTrue("Empty chat is not deleted", ChatService.chats.none { it.id == message41.chatId })
    }

    @Test
    fun deleteChat() {
        // arrange
        val chat = ChatService.chats.find { it.id == message32.chatId } ?: error("Chat not found")

        // act
        val result = ChatService.deleteChat(message32.chatId)

        // assert
        assertTrue("Failed to delete chat", result)
        assertFalse("Chat is detected", ChatService.chats.contains(chat))
    }

    @Test
    fun edit() {
        // arrange
        val newText = "New text for message edit"

        // act
        val result = ChatService.edit(message22, newText)

        // assert
        assertTrue("Failed to edit message", result.text == newText)
    }

    @Test
    fun getChatById() {
        // act
        val result = ChatService.getChatById(message31.chatId)

        // assert
        assertTrue("Chat not found", result.id == message31.chatId)
    }

    @Test
    fun getAllChats() {
        // arrange
        val userId = Jack.id

        // act
        val result = ChatService.getAllChats(userId)

        // assert
        assertTrue("Incorrect count of chats for this User", result.size == 3)
    }
}