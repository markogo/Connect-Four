package ee.taltech.connect_four

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import ee.taltech.connect_four.Game.GameLogic
import ee.taltech.connect_four.Game.GameBoard

class MainActivity : AppCompatActivity() {
    private var game = GameLogic()
    private var totalMovesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun gameEndMessage(): String {
        var message: String
        if (game.checkIfDraw()){
            message = "IT'S A DRAW!"
            return message
        }
        if (game.winner == 1) {
            message = "RED WINS!"
            return message
        }
        message = "YELLOW WINS!"
        return message
    }

    private fun buttonToColor(result: Int, view: View): View {
        return when (result) {
            0 -> view.findViewById(R.id.button6)
            1 -> view.findViewById(R.id.button5)
            2 -> view.findViewById(R.id.button4)
            3 -> view.findViewById(R.id.button3)
            4 -> view.findViewById(R.id.button2)
            else -> return view.findViewById(R.id.button1)
        }
    }

    private fun checkGameOver(fromState: Boolean = false): Boolean {
        var nextMoveByRed = game.checkWin().third
        if (fromState) {
            nextMoveByRed = !nextMoveByRed
        }
        if (game.checkWin().first != null) {
            if (game.checkWin().second == 0)
                showWinningLine(0, nextMoveByRed)
            if (game.checkWin().second == 1)
                showWinningLine(1, nextMoveByRed)
            if (game.checkWin().second == 2)
                showWinningLine(2, nextMoveByRed)
            if (game.checkWin().second == 3)
                showWinningLine(3, nextMoveByRed)
            findViewById<TextView>(R.id.winnerOfGame).text = gameEndMessage()
            return true
        }
        if (game.checkIfDraw()) {
            findViewById<TextView>(R.id.winnerOfGame).text = gameEndMessage()
            return true
        }
        return false
    }

    private fun showWinningLine(value: Int, nextMoveByRed: Boolean) {
        var col: View
        var button: View

        for (pair in game.checkWin().first!!) {
            if (pair[0] == 0)
                col = findViewById(R.id.col1)
            else if (pair[0] == 1)
                col = findViewById(R.id.col2)
            else if (pair[0] == 2)
                col = findViewById(R.id.col3)
            else if (pair[0] == 3)
                col = findViewById(R.id.col4)
            else if (pair[0] == 4)
                col = findViewById(R.id.col5)
            else if (pair[0] == 5)
                col = findViewById(R.id.col6)
            else
                col = findViewById(R.id.col7)

            if (pair[1] == 0)
                button = findViewById(R.id.button6)
            else if (pair[1] == 1)
                button = findViewById(R.id.button5)
            else if (pair[1] == 2)
                button = findViewById(R.id.button4)
            else if (pair[1] == 3)
                button = findViewById(R.id.button3)
            else if (pair[1] == 4)
                button = findViewById(R.id.button2)
            else
                button = findViewById(R.id.button1)

            if (nextMoveByRed) {
                if (value == 0 )
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_red_vertical_line)
                else if ( value == 1)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_red_horizontal_line)
                else if (value == 2)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_red_right_diagonal_line)
                else if (value == 3)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_red_left_diagonal_line)
            }
            else {
                if (value == 0 )
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_yellow_vertical_line)
                else if ( value == 1)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_yellow_horizontal_line)
                else if (value == 2)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_yellow_right_diagonal_line)
                else if (value == 3)
                    col.findViewById<View>(button.id).setBackgroundResource(R.drawable.btn_yellow_left_diagonal_line)
            }
        }
    }

    fun gameButtonOnClick(column: Int, view: View) {
        if (game.gameBoard.buttonNotColoredInBoard(column, game.gameBoard.firstFreeElementInColumn(column)) && game.checkWin().first == null) {

            game.gameBoard.makeMove(column, game.nextMoveByRed) // Update board state

            if (game.nextMoveByRed) {
                buttonToColor(game.gameBoard.firstFreeElementInColumn(column) - 1, view).setBackgroundResource(R.drawable.btn_red_background)
            } else {
                buttonToColor(game.gameBoard.firstFreeElementInColumn(column) - 1, view).setBackgroundResource(R.drawable.btn_yellow_background)
            }
            totalMovesCount++
            findViewById<TextView>(R.id.moveCounter).text = totalMovesCount.toString()
            val gameOver = checkGameOver()
            game.nextMoveByRed = !game.nextMoveByRed
            if (gameOver) {
                changeTurnColor(null, true)
                game.gameStarted = false
            } else {
                changeTurnColor(null)
            }
        }
    }

    fun gameStartOnClick(view: View) {
        game.gameStarted = true
        game.gameBoard.resetBoardState()
        resetBoardColors()
        totalMovesCount = 0
        findViewById<TextView>(R.id.winnerOfGame).text = ""
        findViewById<TextView>(R.id.moveCounter).text = totalMovesCount.toString()
        game.nextMoveByRed = true
        changeTurnColor(null)
        setColumnButtonListeners()
    }

    private fun setColumnButtonListeners() {
        setButtonListeners(findViewById(R.id.col1))
        setButtonListeners(findViewById(R.id.col2))
        setButtonListeners(findViewById(R.id.col3))
        setButtonListeners(findViewById(R.id.col4))
        setButtonListeners(findViewById(R.id.col5))
        setButtonListeners(findViewById(R.id.col6))
        setButtonListeners(findViewById(R.id.col7))
    }

    private fun setButtonListeners(view: View) {
        val button1: View = view.findViewById(R.id.button1)
        val button2: View = view.findViewById(R.id.button2)
        val button3: View = view.findViewById(R.id.button3)
        val button4: View = view.findViewById(R.id.button4)
        val button5: View = view.findViewById(R.id.button5)
        val button6: View = view.findViewById(R.id.button6)

        var column = 0

        if (view == findViewById(R.id.col1)) {
            column = 0
        } else if (view == findViewById(R.id.col2)) {
            column = 1
        } else if (view == findViewById(R.id.col3)) {
            column = 2
        } else if (view == findViewById(R.id.col4)) {
            column = 3
        } else if (view == findViewById(R.id.col5)) {
            column = 4
        } else if (view == findViewById(R.id.col6)) {
            column = 5
        } else if (view == findViewById(R.id.col7)) {
            column = 6
        }

        button1.setOnClickListener { gameButtonOnClick(column, view) }
        button2.setOnClickListener { gameButtonOnClick(column, view) }
        button3.setOnClickListener { gameButtonOnClick(column, view) }
        button4.setOnClickListener { gameButtonOnClick(column, view) }
        button5.setOnClickListener { gameButtonOnClick(column, view) }
        button6.setOnClickListener { gameButtonOnClick(column, view) }
        return
    }

    private fun changeTurnColor(nextMoveByRedFromState: Boolean?, gameOver: Boolean = false) {
        if (gameOver) {
            findViewById<TextView>(R.id.yellowTurn).setBackgroundColor(resources.getColor(R.color.colorDarkYellow))
            findViewById<TextView>(R.id.redTurn).setBackgroundColor(resources.getColor(R.color.colorDarkRed))
        } else {
            if (nextMoveByRedFromState != null) {
                if (nextMoveByRedFromState) {
                    findViewById<TextView>(R.id.yellowTurn).setBackgroundColor(resources.getColor(R.color.colorDarkYellow))
                    findViewById<TextView>(R.id.redTurn).setBackgroundColor(Color.RED)
                } else {
                    findViewById<TextView>(R.id.yellowTurn).setBackgroundColor(Color.YELLOW)
                    findViewById<TextView>(R.id.redTurn).setBackgroundColor(resources.getColor(R.color.colorDarkRed))
                }
            }
            else {
                if (game.nextMoveByRed) {
                    findViewById<TextView>(R.id.yellowTurn).setBackgroundColor(resources.getColor(R.color.colorDarkYellow))
                    findViewById<TextView>(R.id.redTurn).setBackgroundColor(Color.RED)
                } else {
                    findViewById<TextView>(R.id.yellowTurn).setBackgroundColor(Color.YELLOW)
                    findViewById<TextView>(R.id.redTurn).setBackgroundColor(resources.getColor(R.color.colorDarkRed))
                }
            }
        }
    }

    private fun resetBoardColors() {
        resetColumnButtonColors(findViewById(R.id.col1))
        resetColumnButtonColors(findViewById(R.id.col2))
        resetColumnButtonColors(findViewById(R.id.col3))
        resetColumnButtonColors(findViewById(R.id.col4))
        resetColumnButtonColors(findViewById(R.id.col5))
        resetColumnButtonColors(findViewById(R.id.col6))
        resetColumnButtonColors(findViewById(R.id.col7))
    }

    private fun resetColumnButtonColors(column: View) {
        column.findViewById<View>(R.id.button1).setBackgroundResource(R.drawable.btn_background)
        column.findViewById<View>(R.id.button2).setBackgroundResource(R.drawable.btn_background)
        column.findViewById<View>(R.id.button3).setBackgroundResource(R.drawable.btn_background)
        column.findViewById<View>(R.id.button4).setBackgroundResource(R.drawable.btn_background)
        column.findViewById<View>(R.id.button5).setBackgroundResource(R.drawable.btn_background)
        column.findViewById<View>(R.id.button6).setBackgroundResource(R.drawable.btn_background)
    }

    private fun colorBoardFromState() {
        putColorsIn(findViewById(R.id.col1))
        putColorsIn(findViewById(R.id.col2))
        putColorsIn(findViewById(R.id.col3))
        putColorsIn(findViewById(R.id.col4))
        putColorsIn(findViewById(R.id.col5))
        putColorsIn(findViewById(R.id.col6))
        putColorsIn(findViewById(R.id.col7))
    }

    private fun getColumn(view: View): Int {
        if (view == findViewById(R.id.col1)) {
            return 0
        } else if (view == findViewById(R.id.col2)) {
            return 1
        } else if (view == findViewById(R.id.col3)) {
            return 2
        } else if (view == findViewById(R.id.col4)) {
            return 3
        } else if (view == findViewById(R.id.col5)) {
            return 4
        } else if (view == findViewById(R.id.col6)) {
            return 5
        } else {
            return 6
        }
    }

    private fun putColorsIn(view: View) {
        val button1: View = view.findViewById(R.id.button1)
        val button2: View = view.findViewById(R.id.button2)
        val button3: View = view.findViewById(R.id.button3)
        val button4: View = view.findViewById(R.id.button4)
        val button5: View = view.findViewById(R.id.button5)
        val button6: View = view.findViewById(R.id.button6)
        val column: Int = getColumn(view)
        var color: Int
        var btnDrawable: Int

        for (row in 0 until 6) {
            if (game.gameBoard.board[column][row] == 0 || game.gameBoard.board[column][row] == 1 || game.gameBoard.board[column][row] == 2) {
                color = game.gameBoard.board[column][row]
                if (color == 0) {
                    btnDrawable = R.drawable.btn_background
                } else if (color == 1) {
                    btnDrawable = R.drawable.btn_red_background
                } else {
                    btnDrawable = R.drawable.btn_yellow_background
                }
                when (row) {
                    0 -> button6.setBackgroundResource(btnDrawable)
                    1 -> button5.setBackgroundResource(btnDrawable)
                    2 -> button4.setBackgroundResource(btnDrawable)
                    3 -> button3.setBackgroundResource(btnDrawable)
                    4 -> button2.setBackgroundResource(btnDrawable)
                    else ->button1.setBackgroundResource(btnDrawable)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("game", game)
        outState.putSerializable("gameBoard", game.gameBoard)
        outState.putBoolean("turn", game.nextMoveByRed)
        outState.putString("winner", findViewById<TextView>(R.id.winnerOfGame)!!.text.toString())
        outState.putInt("count", totalMovesCount)
        outState.putBoolean("gameStarted", game.gameStarted)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        game = savedInstanceState.getSerializable("game") as GameLogic
        game.gameBoard = savedInstanceState.getSerializable("gameBoard") as GameBoard
        colorBoardFromState()
        findViewById<TextView>(R.id.winnerOfGame).text = savedInstanceState.getString("winner")
        totalMovesCount = savedInstanceState.getInt("count")
        findViewById<TextView>(R.id.moveCounter).text = totalMovesCount.toString()
        val gameStarted = savedInstanceState.getBoolean("gameStarted")
        val turn = savedInstanceState.getBoolean("turn")
        val isGameOver = checkGameOver(true)

        if (gameStarted) {
            changeTurnColor(turn, isGameOver)
            setButtonListeners(findViewById(R.id.col1))
            setButtonListeners(findViewById(R.id.col2))
            setButtonListeners(findViewById(R.id.col3))
            setButtonListeners(findViewById(R.id.col4))
            setButtonListeners(findViewById(R.id.col5))
            setButtonListeners(findViewById(R.id.col6))
            setButtonListeners(findViewById(R.id.col7))
        }
    }
}