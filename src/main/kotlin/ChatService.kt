object ChatService {

    var chats = mutableListOf<Chat>()

    fun getUnreadChatsCount(): Int {
        return chats
            .count { it.messages.any { !it.isRead } }
    }

    fun getChats(userId: Int): List<Chat> {
        return chats
            .filter { it.fromUserId == userId || it.toUserId == userId }
            .filter { it.messages.isNotEmpty() }
    }

    fun getMessages(chatId: Int, lastMessageId: Int, countMessages: Int): List<Message> {
        val chat = chats.find { it.id == chatId } ?: error("Chat not found")

        return chat.messages
            .asSequence()
            .filter { it.chatId == chatId }
            .sortedBy { it.id }
            .filter { it.id >= lastMessageId }
            .take(countMessages)
            .onEach { it.isRead = true }
            .toList()
    }

    fun sendMessage(message: Message): Boolean {
        val chat = chats
            .find { it.id == message.chatId } ?: error("Chat with id[${message.chatId}] not found")
        if (chat.messages.none { it.id == message.id }) {
            chat.messages += message
        }
        return true
    }

    fun deleteMessage(message: Message): Boolean {
        val chat = chats.find { it.id == message.chatId } ?: return false
        chat.messages.find { it.id == message.id } ?: return false

        chat.messages = chat.messages.filter { it.id != message.id } as MutableList<Message>
        if (chat.messages.isEmpty()) {
            chats.remove(chat)
        }
        return true
    }

    fun deleteChat(chatId: Int): Boolean {
        val chat = chats.find { it.id == chatId } ?: error("Chat not found")

        chat.messages = chat.messages
            .filter { it.chatId != chatId }
            .toMutableList()
        chats = chats
            .filter { it.id != chatId }
            .toMutableList()
        return true
    }

    fun edit(message: Message, text: String): Message {
        val chat = chats.find { it.id == message.chatId } ?: error("Chat not found")
        val msg = chat.messages.find { it.id == message.id } ?: error("Message not found")
        msg.text = text
        return msg
    }

    fun getChatById(id: Int): Chat {
        return chats.find { it.id == id } ?: error("Chat not found")
    }

    fun getAllChats(userId: Int): List<Chat> {
        return chats.filter { it.fromUserId == userId || it.toUserId == userId}
    }
}