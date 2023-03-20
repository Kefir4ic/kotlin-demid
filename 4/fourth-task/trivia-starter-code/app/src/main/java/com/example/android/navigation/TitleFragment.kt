package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
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
//                        реализация до подключения плагина для передачи данных
//            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
            Navigation.findNavController(it).navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }
//        выводим меню на главный экран
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController()) ||
                super.onOptionsItemSelected(item)
    }
}