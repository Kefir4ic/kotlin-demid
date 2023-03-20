package com.example.second_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {
//    создание и запуск приложения
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//    получение id кнопки
        val rollButton: Button = findViewById(R.id.roll_button)

//    добавление обработчика на нажатие кнопки
        rollButton.setOnClickListener {
//            генерация рандомного числа от 1 до 6
            val result = Random().nextInt(6) + 1
//            получение id текстового поля, куда писат результаты броска
            val resultText = findViewById<TextView>(R.id.result_text)
//            запись в поле результата броска
            resultText.text = result.toString()

//            получение id поля с картинкой
            val resultImage = findViewById<ImageView>(R.id.result_image)
//            КОД МОЖНО УПРОСТИТЬБ ПОЭТОМУ ПЕРВОНАЧАЛЬНЫЙ ВАРИАНТ УСТАНОВКИ КАРТИНОК ЗАКОММЕНТИЛ
//            в зависимости от результата ставим картинку
//            when (result) {
//               1 -> resultImage.setImageResource(R.drawable.dice_1)
//               2 -> resultImage.setImageResource(R.drawable.dice_2)
//               3 -> resultImage.setImageResource(R.drawable.dice_3)
//               4 -> resultImage.setImageResource(R.drawable.dice_4)
//               5 -> resultImage.setImageResource(R.drawable.dice_5)
//               6 -> resultImage.setImageResource(R.drawable.dice_6)
////                на случай если буду играть с кодом и изменю макс число на кубике,
////                чтобы не сломать изображения
//               else -> resultImage.setImageResource(R.drawable.empty_dice)
//            }

//            тк when умеет возвращать какое-то значение
//            пусть возвращает ссылку на картинку при разных вариантах
//            но в таком случае else - ОБЯЗАТЕЛЬНО
            var diceRollImage = when (result) {
               1 -> R.drawable.dice_1
               2 -> R.drawable.dice_2
               3 -> R.drawable.dice_3
               4 -> R.drawable.dice_4
               5 -> R.drawable.dice_5
               6 -> R.drawable.dice_6
               else -> R.drawable.empty_dice
            }
//            устанавливаем картинку по ссылке из when
            resultImage.setImageResource(diceRollImage)
        }
    }

}