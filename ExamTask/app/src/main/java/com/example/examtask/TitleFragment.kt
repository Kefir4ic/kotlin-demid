package com.example.examtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.examtask.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        инициализируем DataBinding для быстрого доступа к элементам на макете
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container, false)

        binding.goToConverterButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_calculatorFragment)
        }

        binding.goToAddValueButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_addValueFragment)
        }

        binding.goToHelpButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_helpFragment)
        }
        return binding.root
    }
}