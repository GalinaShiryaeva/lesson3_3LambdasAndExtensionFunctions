data class Message(
    var id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val chatId: Int,
    var isRead: Boolean = false,
    var text: String
) {
    init {
        if (ChatService.chats.none { it.id == this.chatId }
        ) {
            ChatService.chats += Chat(
                this.chatId,
                this.fromUserId,
                this.toUserId,
                mutableListOf()
            )
        }
    }

    override fun toString(): String {
        return "Message(id=$id, fromUserId=$fromUserId, toUserId=$toUserId, chatId=$chatId, isRead=$isRead, text='$text')\n"
    }
}