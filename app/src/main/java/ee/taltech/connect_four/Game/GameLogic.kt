package ee.taltech.connect_four.Game

import java.io.Serializable
import kotlin.collections.ArrayList

class GameLogic : Serializable {
    var gameBoard = GameBoard()
    var winner = 0 // 0 - No winner, 1 - Red wins, 2 - Yellow wins

    var nextMoveByRed = true;
    var gameStarted = false


    fun checkWin(): Triple<ArrayList<Array<Int>>?, Int?, Boolean> {
        /*
        0 - VERTICAL
        1 - HORIZONTAL
        2 - BOTTOM LEFT TO UPPER RIGHT DIAGONAL
        3 - BOTTOM RIGHT TO UPPER LEFT DIAGONAL
         */
        if (verticalCheck().first)
            return Triple(verticalCheck().second, 0, nextMoveByRed)
        if (horizontalCheck().first)
            return Triple(horizontalCheck().second, 1, nextMoveByRed)
        if (diagonalLowerLeftToUpperRight().first)
            return Triple(diagonalLowerLeftToUpperRight().second, 2, nextMoveByRed)
        if (diagonalUpperLeftToLowerRight().first)
            return Triple(diagonalUpperLeftToLowerRight().second, 3, nextMoveByRed)

        return Triple(null, null, nextMoveByRed)
    }


    private fun verticalCheck(): Pair<Boolean, ArrayList<Array<Int>>?> {
        val winningTokens = arrayListOf<Array<Int>>()
        var totalInRow = 0
        var currentColor: Int
        for (col in 0 until 7) {
            for (row in 0 until 6) {
                if (totalInRow == 0) {
                    totalInRow++
                }
                currentColor = gameBoard.board[col][row]
                if (row + 1 < 6 && gameBoard.board[col][row + 1] == currentColor && currentColor != 0) {
                    totalInRow++
                    winningTokens.add(arrayOf(col, row))
                    if (totalInRow == 4) {
                        winningTokens.add(arrayOf(col, row + 1))
                        winner = currentColor
                        return Pair(true, winningTokens)
                    }
                    continue
                }
                winningTokens.clear()
                totalInRow = 0
            }
        }
        return Pair(false, null)
    }

    private fun horizontalCheck(): Pair<Boolean, ArrayList<Array<Int>>?> {
        val winningTokens = arrayListOf<Array<Int>>()
        var totalInRow = 0
        var currentColor: Int
        for (row in 0 until 6) {
            for (col in 0 until 7) {
                if (totalInRow == 0) {
                    totalInRow++
                }
                currentColor = gameBoard.board[col][row]
                if (col + 1 < 7 && gameBoard.board[col + 1][row] == currentColor && currentColor != 0) {
                    totalInRow++
                    winningTokens.add(arrayOf(col, row))
                    if (totalInRow == 4) {
                        winningTokens.add(arrayOf(col + 1, row))
                        winner = currentColor
                        return Pair(true, winningTokens)
                    }
                    continue
                }
                winningTokens.clear()
                totalInRow = 0
            }
        }
        return Pair(false, null)
    }


    private fun diagonalUpperLeftToLowerRight(): Pair<Boolean, ArrayList<Array<Int>>?>  {
        val winningTokens = arrayListOf<Array<Int>>()
        var totalInRow = 0
        var currentColor: Int
        for (col in 0 until 7) {
            for (row in 0 until 6) {
                if (totalInRow == 0) {
                    totalInRow++
                }
                currentColor = gameBoard.board[col][row]
                if (currentColor == 0) { // No color
                    break
                }
                for (x in 1 until 4) {
                    if (col + x < 7 && row - x >= 0 && gameBoard.board[col + x][row - x] == currentColor) {
                        totalInRow++
                        winningTokens.add(arrayOf(col + x, row - x))
                        if (totalInRow == 4) {
                            winningTokens.add(arrayOf(col, row))
                            winner = currentColor
                            return Pair(true, winningTokens)
                        }
                    }
                    continue
                }
                winningTokens.clear()
                totalInRow = 0
            }
        }
        return Pair(false, null)
    }

    private fun diagonalLowerLeftToUpperRight(): Pair<Boolean, ArrayList<Array<Int>>?> {
        val winningTokens = arrayListOf<Array<Int>>()
        var totalInRow = 0
        var currentColor: Int
        for (col in 0 until 7) {
            for (row in 0 until 6) {
                if (totalInRow == 0) {
                    totalInRow++
                }
                currentColor = gameBoard.board[col][row]
                if (currentColor == 0) { // No color
                    break
                }
                for (x in 1 until 4) {
                    if (col + x < 7 && row + x < 6 && gameBoard.board[col + x][row + x] == currentColor) {
                        totalInRow++
                        winningTokens.add(arrayOf(col + x, row + x))
                        if (totalInRow == 4) {
                            winningTokens.add(arrayOf(col, row))
                            winner = currentColor
                            return Pair(true, winningTokens)
                        }
                    }
                    continue
                }
                winningTokens.clear()
                totalInRow = 0
            }
        }
        return Pair(false, null)
    }

    fun checkIfDraw() : Boolean{
        var tokenCount = 0
        for (col in 0 until 7){
            for(row in 0 until 6){
                if (gameBoard.board[col][row] == 1 || gameBoard.board[col][row] == 2){
                    tokenCount++
                }
            }
        }
        if (tokenCount == 42) { // Number of max tokens on board
            return true
        }
        return false
    }
}