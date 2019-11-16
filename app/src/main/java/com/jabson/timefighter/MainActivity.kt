package com.jabson.timefighter

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton: Button
    internal lateinit var restartButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score = 0
    internal var gameStarted = false
    internal var gameEnded = false
    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 30000
    internal var countDownInterval: Long = 1000

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        resetGame()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapMeButton = findViewById<Button>(R.id.tap_me_button)
        restartButton = findViewById<Button>(R.id.restart_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)

        resetGame()

        tapMeButton.setOnClickListener{
            view ->
            incrementScore()
        }

        restartButton.setOnClickListener{
            view ->
            countDownTimer.cancel()
            resetGame()
        }
    }

    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.sua_pontuacao, score.toString())
        var initialTimeLeft = initialCountDown / 1000;
        timeLeftTextView.text = getString(R.string.tempo_restante, initialTimeLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                var timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.tempo_restante, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
        gameEnded = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Fim do Jogo!")
            setMessage(getString(R.string.mensagem_fim_jogo, score.toString()))
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }

        gameEnded = true
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }

        if (!gameEnded) {
            score = score + 1
            var newScore = getString(R.string.sua_pontuacao, score.toString())
            gameScoreTextView.text = newScore
        }
    }
}
