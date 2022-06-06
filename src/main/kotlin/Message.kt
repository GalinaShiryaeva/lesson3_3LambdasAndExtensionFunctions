data class Message(
    var id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val chatId: Int,
    var isRead: Boolean = false,
    var text: String
) {
    init {
        ChatService.messages += this
        if (ChatService.chats.none { it.id == this.chatId }
        ) {
            ChatService.chats += Chat(
                this.chatId,
                this.fromUserId,
                this.toUserId,
                ChatService.messages
            )
        }
    }
}