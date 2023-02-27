package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.android.navigation.databinding.FragmentTitleBinding
import com.example.android.navigation.databinding.FragmentTitleBindingImpl

// класс для отображения страницы Title
class TitleFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        инициализируем DataBinding для быстрого доступа к элементам на макете
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container, false)

//        добавляем обработчик перехода к другой странице с помощью навигации по нажатию кнопки
        binding.playButton.setOnClickListener{
//            нашли навигационный контроллер(findNavController),
//            выполнили навигацию по контроллеру(navigate),
//            перешли куда надо с помощью идентификатора
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
        }
        return binding.root
    }


}