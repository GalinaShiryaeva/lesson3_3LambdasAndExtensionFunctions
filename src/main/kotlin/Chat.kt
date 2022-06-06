data class Chat(
    var id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    var messages: MutableList<Message>
) {
    init {
        this.messages = ChatService.messages
            .filter { it.chatId == this.id } as MutableList<Message>
    }
}