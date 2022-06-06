import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    // arrange
    val Anna = User(1, "Anna")
    val Jack = User(2, "Jack")
    val Kate = User(3, "Kate")
    val Alex = User(4, "Alex")

    val messageAJ1 = Message(11, Anna.id, Jack.id, 11, false, "Hey there!")
    val messageAJ2 = Message(12, Anna.id, Jack.id, 11, true, "How are you?")
    val messageAK1 = Message(21, Anna.id, Kate.id, 12, false, "Hey Kate!")
    val messageKA1 = Message(22, Kate.id, Anna.id, 12, true, "Hi!")
    val messageKA2 = Message(23, Kate.id, Anna.id, 12, false, "Hi!")
    val messageJK1 = Message(31, Jack.id, Kate.id, 13, true, "Hi!")
    val messageJK2 = Message(32, Jack.id, Kate.id, 13, true, "Hello")
    val messageJA1 = Message(41, Jack.id, Alex.id, 14, false, "Hello")

    @Test
    fun getUnreadChatsCount() {
        // act
        val result = ChatService.getUnreadChatsCount()

        // assert
        assertTrue("Failed to perform request", result > 0)
    }

    @Test
    fun getChats() {
        // act
        val result = ChatService.getChats(Kate.id)

        // assert
        assertTrue("Failed to perform request", result.isNotEmpty())
    }

    @Test
    fun getMessages() {
        // act
        val result = ChatService.getMessages(12, 21, 2)
        println(result)
        println(result.size)

        // assert
    }

    @Test
    fun sendMessage() {
        // act
        ChatService.sendMessage(messageAJ1)

        // assert
        assertTrue("Message is not existing", ChatService.messages.contains(messageAJ1))
        assertTrue("Chat is not existing", ChatService.chats.any { it.id == messageAJ1.chatId })
    }

    @Test
    fun deleteMessage() {
        // arrange

        // act

        // assert
    }

    @Test
    fun deleteChat() {
        // arrange

        // act

        // assert
    }
}