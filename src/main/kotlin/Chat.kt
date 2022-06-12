data class Chat(
    var id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    var messages: MutableList<Message> = mutableListOf()
)