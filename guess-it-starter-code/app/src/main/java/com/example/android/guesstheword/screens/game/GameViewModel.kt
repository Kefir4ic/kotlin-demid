package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel

// класс для viewmodel, отвечающий за обработку и работу с данными
class GameViewModel: ViewModel() {

//    класс LiveData позволяет следить за изменением данных,
//    что позволит избавиться от методов, изменяющих значени в текстовых полях во View

    // The current word
    private var _word = MutableLiveData<String>("")
    val word: LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    // флаг события, что игра завершилась
    private var _gameFinished = MutableLiveData<Boolean>(false)
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    private var _secondsUntilEnd = MutableLiveData<Long>()
    val secondsUntilEnd: LiveData<Long>
        get() = _secondsUntilEnd

    private var timer: CountDownTimer

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
            _gameFinished.value = true
//            gameFinished()
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

//    в этом блоке задаются статические переменные
    companion object {
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val GAME_TIME = 10 * ONE_SECOND
    }

    init {
        resetList()
        nextWord()

// понимаем, колько секунд будет длиться игра
        _secondsUntilEnd.value = GAME_TIME / ONE_SECOND

//        создаем обьект таймера и реаилзуем для него методы
        timer = object: CountDownTimer(GAME_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _gameFinished.value = true
            }
        }

        timer.start()

//        сообщение при инициализации
        Log.i("TAG", "GameViewModel.init")
    }

    override fun onCleared() {
        super.onCleared()
//        сообщение при создании
        Log.i("TAG", "GameViewModel.onCleared")
    }

    fun onGameFinishedComplete() {
        _gameFinished.value = false
    }
}