import java.util.function.Predicate

object ChatService {

    var chats = mutableListOf<Chat>()
    var messages = mutableListOf<Message>()

    fun getUnreadChatsCount(): Int {
        return chats
            .count {
                it.messages
                    .any { !it.isRead }
            }
    }

    fun getChats(userId: Int): List<Chat> {
        return chats
            .filter { it.fromUserId == userId || it.toUserId == userId }
            .filter { it.messages.isNotEmpty() }
    }

    fun getMessages(chatId: Int, lastMessageId: Int, countMessages: Int): List<Message> {
        val chat = chats.find { it.id == chatId } ?: error("Chat not found")

        return chat.messages
            .sortedBy { it.id }
            //.dropWhile { it.id < lastMessageId }
            .filter { it.id > lastMessageId } // or .dropWhile { it.id < lastMessageId }
            .take(countMessages)
            .onEach { it.isRead = true }

    }

    fun sendMessage(message: Message): Boolean {
        if (chats.isEmpty() || chats.any { it.id != message.chatId }) {
            chats += Chat(message.chatId, message.fromUserId, message.toUserId, mutableListOf())
            if (messages.none { it.id == message.id }) {
                messages += message
            }
            return true
        }
        return false
    }


    fun deleteMessage(message: Message): Boolean {
        val chatId = message.chatId
        messages.removeIf(Predicate { it.id == message.id })
        chats
            .first { it.id == chatId }
            .run {
                if (this.messages.isEmpty()) {
                    return chats.remove(this)
                }
            }
        return false
    }

    fun deleteChat(chatId: Int): Boolean {
        messages = messages
            .filter { it.chatId != chatId }
            .toMutableList()
        chats = chats
            .filter { it.id != chatId } as MutableList<Chat>
        return true
    }

    fun edit(message: Message, text: String): Message {
        return messages
            .filter { it.id == message.id }
            .map { it.text = text }
            .first() as Message
    }

    fun deleteMessage(messageId: Int): Boolean {
        messages = messages.filter { it.id != messageId } as MutableList<Message>
        return true
    }

    fun createChat(chat: Chat): Boolean {
        chats += chat
        return true
    }

    fun getChatById(id: Int): Chat? {
        return chats.find { it.id == id }
    }

    fun getAllChats(userId: Int): List<Chat> {
        return chats
            .filter { it.fromUserId == userId }
    }
}