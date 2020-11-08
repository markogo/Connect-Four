package ee.taltech.connect_four.Game

import java.io.Serializable

class GameBoard : Serializable {

    /*
   BOARD TOKEN STATES

   0 - EMPTY
   1 - RED
   2 - YELLOW
    */

    var board = arrayOf(
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0)
    )

    fun resetBoardState(){
        for (col in 0 until 7) {
            for (row in 0 until 6) {
                board[col][row] = 0
            }
        }
    }

    fun makeMove(column: Int, nextMoveByRed: Boolean) {
        if (nextMoveByRed) {
            board[column][firstFreeElementInColumn(column)] = 1
        }
        else {
            board[column][firstFreeElementInColumn(column)] = 2
        }
    }

    fun firstFreeElementInColumn(column: Int): Int {
        var result = 0
        for (row in 0 until 6) {
            if (board[column][row] == 1 || board[column][row] == 2) {
                continue
            }
            result = row
            break
        }
        return result
    }

    fun buttonNotColoredInBoard(column: Int, row: Int): Boolean {
        if (board[column][row] == 0) {
            return true
        }
        return false
    }
}