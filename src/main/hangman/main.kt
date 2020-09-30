package hangman

import java.lang.NumberFormatException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    var action = "Y"
    var userManager = UserManager()

    while (action == "Y" || action == "H") {
        if (action == "Y") {
            var result = play(userManager)
            if (result == "won") {
                println("Congratulations! Want to play again? (Y/N/H):")
            } else println("Sorry, you lost… The word was: ${result}\n")
        } else {
            score(userManager)
        }
        println("Want to play again? (Y/N/H):")
        action = readLine().toString()
    }
    println("Thanks for playing!")
}

fun play(userManager: UserManager): String {
    println("Enter Name:")
    var name = readLine()
    while (name == null || name.isEmpty()) {
        println("Please, enter Name:")
        name = readLine()
    }
    println("Hello ${name}, Let’s play Hangman!")

    println("Enter Word:")
    var word = readLine()
    while (word == null || word.isEmpty()) {
        println("Please, enter word:")
        word = readLine()
    }

    var wordToDisplay = "_".repeat(word.length);

    println("Game is starting…")

    println("Please, enter lives")

    var lives: String? = ""
    var numeric = false
    while (lives == null || lives.isEmpty() || !numeric) {
        lives = readLine()

        if (lives != null) {
            try {
                var livesInput = lives.toInt()
                numeric = true
            } catch (e: NumberFormatException) {
                numeric = false
                println("Please, enter valid number")

            }
        }
    }

    println("Lives remaining: ${lives}")

    var user = User(name, lives.toInt())

    var triedChars = mutableSetOf<Char>()
    var char = ' '
    var win = false
    println("Current Word is: $wordToDisplay")
    while (!win && user.lives > 0) {
        println("Enter Character:")
        var inputChar = readLine()
        while (inputChar == null || inputChar.length != 1) {
            println("Please, enter Character:")
            inputChar = readLine()
        }
        char = inputChar.toCharArray()[0]
        if (char !in triedChars) {
            if (word.contains(char)) {
                println("Yes, it is there!!!")
                var index = word.indexOf(char);
                while (index >= 0) {
                    try {
                        wordToDisplay = wordToDisplay.substring(0, index) + char + wordToDisplay.substring(index + 1)
                        index = word.indexOf(char, index + 1);
                        if (wordToDisplay.indexOf('_') < 0) {
                            userManager.win(user.name)
                            win = true
                        }
                    } catch (e: StringIndexOutOfBoundsException) {
                        break
                    }
                }
                triedChars.add(char)
                println("Current Word is: $wordToDisplay")
            } else {
                println("There is no such character")
                user.miss()
                println("Lives remaining: ${user.lives}")
            }
        } else {
            println("You already tried this character")
            println("Lives remaining: ${user.lives}")
        }

    }
    return if (win) "won" else word
}

fun score(userManager: UserManager) {
    var scores = userManager.getScore().toList().sortedBy { (_, value) -> value }.toMap()
    for ( name in scores) {
        println("${name.key} - ${name.value} lives")
    }
}
