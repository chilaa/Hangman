package hangman

class UserManager {
    var users: MutableMap<String, Int> = LinkedHashMap()

    fun win(user: String) {

        if (users.containsKey(user)) {
            var i = users[user]?.toInt()
            if (i == null) {
                i = 0
            }

            users[user] = i + 1
            println(users[user])
        } else {
            users[user] = 1
        }

    }

    fun getScore(): MutableMap<String, Int> {
        return users
    }
}