package com.example.jogo_da_velha

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button
    private lateinit var gameButtons: Array<Button>
    private var currentPlayer = "X"
    private var board = arrayOfNulls<String>(9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando os elementos
        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)
        gameButtons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        // Configurar o clique de cada botão do tabuleiro
        for ((index, button) in gameButtons.withIndex()) {
            button.setOnClickListener { onGameButtonClick(index) }
        }

        // Clique no botão de reiniciar
        resetButton.setOnClickListener { resetGame() }

        updateStatus()
    }

    private fun onGameButtonClick(index: Int) {
        if (board[index] != null) return  // Evitar jogadas repetidas

        board[index] = currentPlayer
        gameButtons[index].apply {
            text = currentPlayer
            setTextColor(if (currentPlayer == "X") Color.parseColor("#7B68EE") else Color.parseColor("#FF00FF"))
        }

        if (checkWin()) {
            statusTextView.text = if (currentPlayer == "X") "Jogador X é o vencedor!" else "Jogador O é o vencedor!"
            statusTextView.setTextColor(if (currentPlayer == "X") Color.parseColor("#7B68EE") else Color.parseColor("#FF00FF"))
            resetButton.visibility = View.VISIBLE
        } else if (board.all { it != null }) {
            statusTextView.text = "Deu velha!"
            statusTextView.setTextColor(Color.WHITE)
            resetButton.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            updateStatus()
        }
    }

    private fun updateStatus() {
        statusTextView.apply {
            text = if (currentPlayer == "X") "Sua vez, Jogador X!" else "Agora é você, Jogador O!"
            setTextColor(if (currentPlayer == "X") Color.parseColor("#7B68EE") else Color.parseColor("#FF00FF"))
        }
    }

    private fun checkWin(): Boolean {
        val winningPositions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Linhas
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Colunas
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)  // Diagonais
        )

        for (positions in winningPositions) {
            val (a, b, c) = positions
            if (board[a] == currentPlayer && board[b] == currentPlayer && board[c] == currentPlayer) {
                // Marca a linha vencedora com a cor do jogador
                val winningColor = if (currentPlayer == "X") Color.parseColor("#7B68EE") else Color.parseColor("#FF00FF")
                gameButtons[a].setBackgroundColor(winningColor)
                gameButtons[b].setBackgroundColor(winningColor)
                gameButtons[c].setBackgroundColor(winningColor)
                return true
            }
        }
        return false
    }

    private fun resetGame() {
        board.fill(null)
        currentPlayer = "X"
        updateStatus()

        // Limpa o tabuleiro
        for (button in gameButtons) {
            button.text = ""
            button.setBackgroundColor(Color.TRANSPARENT)
        }
        resetButton.visibility = View.GONE
    }
}