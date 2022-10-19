package minesweeper

class MineSweeper (var mines: Int) {
    val mineFieldHidden = MutableList(9) {MutableList(9) {"."}}
    val mineFieldTemp = (List(81-mines) {"."} + List(mines) {"X"}).shuffled().chunked(9)
    var minesFound: Int = 0
    val mineField: MutableList<MutableList<String>> = mutableListOf()

    init {
        for (list in mineFieldTemp) {
            mineField.add(list.toMutableList())
        }
        for (x in 0..8) {
            for (y in 0..8) {
                if (mineField[x][y] == "X") {
                    for (xPos in listOf(x, x - 1, x + 1)) {
                        for (yPos in listOf(y, y - 1, y + 1)) {
                            if (xPos in 0..8 && yPos in 0..8) {
                                if (mineField[xPos][yPos] == ".") {
                                    mineField[xPos][yPos] = "1"
                                    mineFieldHidden[xPos][yPos] = "1"
                                } else if (mineField[xPos][yPos] != "X") {
                                    mineField[xPos][yPos] = (mineField[xPos][yPos].toInt() + 1).toString()
                                    mineFieldHidden[xPos][yPos] = (mineField[xPos][yPos].toInt() + 1).toString()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun printField () {
        val coordinatesH = " │123456789│"
        val delimiter = "—│—————————│"
        println(coordinatesH)
        println(delimiter)
        for (h in 1..this.mineFieldHidden.size) {
            println("$h|${this.mineFieldHidden[h-1].joinToString("")}|")
        }
        println(delimiter)
    }

    fun setMark (y: Int, x: Int): Boolean {
        var xPos = x - 1
        var yPos = y - 1
        if (mineFieldHidden[xPos][yPos].toIntOrNull() != null) {
            println("There is a number here!")
        } else if (mineFieldHidden[xPos][yPos] == "*") {
            mineFieldHidden[xPos][yPos] = "."
        } else {
            mineFieldHidden[xPos][yPos] = "*"
            if (mineField[xPos][yPos] == "X") minesFound++
        }
        if (minesFound == mines) return true
        return false
    }

}

fun main() {
    println("How many mines do you want on the field?")
    val minesCount = readln().toInt()
    var mineSweeper = MineSweeper(minesCount)
    mineSweeper.printField()

    while (true) {
        println("Set/delete mines marks (x and y coordinates):")
        var (x, y) = readln().split(" ").map {it.toInt()}
        var result = mineSweeper.setMark(x, y)
        mineSweeper.printField()
        if (result) {
            println("Congratulations! You found all the mines!")
            break
        }
    }

}