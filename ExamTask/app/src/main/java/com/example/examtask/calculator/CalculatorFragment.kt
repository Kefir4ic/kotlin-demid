package com.example.examtask.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.examtask.R
import com.example.examtask.databinding.FragmentAddValueBinding
import com.example.examtask.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCalculatorBinding>(
            inflater, R.layout.fragment_calculator, container, false)




        return binding.root
    }

}