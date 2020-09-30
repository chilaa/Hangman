package hangman

class User(var name: String,var lives: Int) {
    var score: Int = 0
    fun miss() = lives--
}