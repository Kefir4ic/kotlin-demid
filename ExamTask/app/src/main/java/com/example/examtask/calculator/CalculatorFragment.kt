package com.example.examtask.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examtask.R
import com.example.examtask.database.CurrencyValueDatabase
import com.example.examtask.databinding.FragmentAddValueBinding
import com.example.examtask.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: FragmentAddValueBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCalculatorBinding>(
            inflater, R.layout.fragment_calculator, container, false)

        val application = requireNotNull(this.activity).application
        val dao = CurrencyValueDatabase.getInstance(application).getCurrencyValueDatabaseDao()
        val viewModelFactory = CalculatorViewModelFactory(dao, application)


        var selectedGroup = ""
        var selectedTo = ""
        var selectedFrom = ""


        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CalculatorViewModel::class.java)

        viewModel.createAdapterForGroupSpinner().observe(viewLifecycleOwner, Observer {
            val groupSpinner = binding.selectGroupSpinner
            groupSpinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it)
            groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
                { selectedGroup = it[position] }
                override fun onNothingSelected(p0: AdapterView<*>?) {} }
        })


        binding.selectGroupButton.setOnClickListener {
            val fromValueSpinner = binding.selectFromSpinner
            val toValueSpinner = binding.selectToSpinner

            viewModel.createAdapterForValuesSpinner(selectedGroup).observe(viewLifecycleOwner, Observer {
                toValueSpinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it)
                toValueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
                    { selectedTo = it[position] }
                    override fun onNothingSelected(p0: AdapterView<*>?) {} }

                fromValueSpinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it)
                fromValueSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
                    { selectedFrom = it[position] }
                    override fun onNothingSelected(p0: AdapterView<*>?) {} }
            })
        }

        binding.calculateButton.setOnClickListener {
            binding.resultText.text = viewModel.onCalculate(
                selectedGroup,
                selectedFrom,
                selectedTo,
                binding.enterValueText.text.toString())
        }
        return binding.root
    }
}