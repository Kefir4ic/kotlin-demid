package com.example.third_task

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.third_task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    задаем отложенный databinding
//    команда lateinit позволят делать отложенную инициализацию, а не сразу присваивать значение
    private lateinit var binding: ActivityMainBinding

//    обьект user, которого м передадим в макет
    private val userFromActivity: UserName = UserName("Maxim")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        это тоже самое что и DataBinding, поэтому можно убрать
//        setContentView(R.layout.activity_main)

//        создает вывод activity_main, показывает его и возвращает DataBinding
//        DataBinding позволяет получать доступ к элементам на макете без использваония findViewById
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        задаем user на макете значение нашего userFromActivity
        binding.userName = userFromActivity

//        пример получения элементов макета через binding
        val doneButton = binding.doneButton
        val editNickname = binding.editNickname
        val doneText = binding.doneText

//        получаем кнопку по ее id
        val done_button = findViewById<Button>(R.id.done_button)

//        можно и без указания имени, а просто it - имя в котлине по умолчанию для таких случаев
//        done_button.setOnClickListener{ it ->
//            addNickname(it)
//        }

//        добавляем обработчик для кнопки
        done_button.setOnClickListener{ button ->
            addNickname(button as Button)
        }

//        более короткий, удобный и правильный обработчик нажатия кнопки
//        done_button.setOnClickListener{
//            shortAddNickname()
//        }
    }

//    функция для обработчика
    private fun addNickname(button: Button) {
//    получаем поле для изменения текста
        val nicknameEditView = findViewById<EditText>(R.id.edit_nickname)
//    получаем поле, куда запишем имя
        val nicknameTextView = findViewById<TextView>(R.id.done_text)
//    записываем введенный текст в поле для имени
        nicknameTextView.text = nicknameEditView.text

//    убираем кнопку и поле для ввода с экрана
        nicknameEditView.visibility = View.GONE
        button.visibility = View.GONE
//    добавляем поле для имени на экран
        nicknameTextView.visibility = View.VISIBLE

//    получаем сервис ввода (клавиатуру)
    val claviatura = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    прячем клавиатуру после нажатия кнопки
    claviatura.hideSoftInputFromWindow(button.windowToken, 0)
    }

    private fun shortAddNickname() {
//        чтобы не писать слово binding много раз (засоряет код, делает его трудно читаемым
//        можно обернуть в специальное выражение
        binding.apply {
            doneText.text = editNickname.text
            editNickname.visibility = View.GONE
            doneText.visibility = View.VISIBLE
            doneButton.visibility = View.GONE
        }
        //    получаем сервис ввода (клавиатуру)
        val claviatura = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //    прячем клавиатуру после нажатия кнопки
        claviatura.hideSoftInputFromWindow(binding.doneButton.windowToken, 0)
    }
}