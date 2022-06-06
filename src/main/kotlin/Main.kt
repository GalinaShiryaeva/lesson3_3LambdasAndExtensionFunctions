fun main() {
    val Anna = User(1, "Anna")
    val Jack = User(2, "Jack")
    val Kate = User(3, "Kate")

    val messageAJ1 = Message(11, Anna.id, Jack.id, 11, false, "Hey there!")
    val messageAJ2 = Message(12, Anna.id, Jack.id, 11, true, "How are you?")
    val messageAK1 = Message(21, Anna.id, Kate.id, 12, false, "Hey Kate!")
    val messageKA1 = Message(22, Kate.id, Anna.id, 12, true, "Hi!")
    val messageJK1 = Message(31, Jack.id, Kate.id, 13, true, "Hi!")
    val messageJK2 = Message(32, Jack.id, Kate.id, 13, false, "Hello")

    val l = ChatService.sendMessage(messageAJ1)

    println("send message: $l")
    println("messages size = " + ChatService.messages.size)
    println("chats size = " + ChatService.chats.size)
}