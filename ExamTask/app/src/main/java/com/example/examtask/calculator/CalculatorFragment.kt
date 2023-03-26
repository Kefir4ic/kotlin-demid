package com.example.examtask.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examtask.R
import com.example.examtask.database.CurrencyValueDatabase
import com.example.examtask.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: FragmentCalculatorBinding

    private var selectedGroup = ""
    private var selectedTo = ""
    private var selectedFrom = ""
    private var enterValueText = ""
    private var enterRoundingText = ""
    private var resultText = ""
    private var groupsList = listOf<String>()
    private var fromList = listOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val messages: Map<String, String> = mapOf(
            "Empty group name!" to getString(R.string.empty_group_name),
            "Empty from name!" to getString(R.string.empty_from_name),
            "Empty to name!" to getString(R.string.empty_to_name),
            "Empty value!" to getString(R.string.empty_value),
            "Value is not Numeric!" to getString(R.string.value_not_numeric),
            "No value in memory!" to getString(R.string.db_problem))

        val binding = DataBindingUtil.inflate<FragmentCalculatorBinding>(
            inflater, R.layout.fragment_calculator, container, false)

        val application = requireNotNull(this.activity).application
        val dao = CurrencyValueDatabase.getInstance(application).getCurrencyValueDatabaseDao()
        val viewModelFactory = CalculatorViewModelFactory(dao, application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CalculatorViewModel::class.java)

        if (savedInstanceState != null) {
            _setInstantState(savedInstanceState, binding, viewModel)
        }

        viewModel.createAdapterForGroupSpinner().observe(viewLifecycleOwner, Observer {
            val groupSpinner = binding.selectGroupSpinner
            _createGroupSpinner(groupSpinner, it)
        })


        binding.selectGroupButton.setOnClickListener {
            val fromValueSpinner = binding.selectFromSpinner
            val toValueSpinner = binding.selectToSpinner

            viewModel.createAdapterForValuesSpinner(selectedGroup).observe(viewLifecycleOwner, Observer {
                fromList = it
                _createToAdapter(toValueSpinner, it)

                _createFromAdapter(fromValueSpinner, it)
            })

            binding.selectFromText.visibility = View.VISIBLE
            binding.selectFromSpinner.visibility = View.VISIBLE
            binding.selctToText.visibility = View.VISIBLE
            binding.selectToSpinner.visibility = View.VISIBLE

            binding.enterValueText.visibility = View.VISIBLE
            binding.enterRondingTextView.visibility = View.VISIBLE
            binding.enterRoundingText.visibility = View.VISIBLE

            binding.calculateButton.visibility = View.VISIBLE
            binding.resultText.visibility = View.VISIBLE
        }

        binding.calculateButton.setOnClickListener {
            viewModel.getValues(selectedGroup, selectedFrom, selectedTo).observe(viewLifecycleOwner, Observer {
                val result = viewModel.onCalculate(
                    selectedGroup,
                    selectedFrom,
                    selectedTo,
                    binding.enterValueText.text.toString(),
                    binding.enterRoundingText.text.toString(),
                    it)
                val text: String = if (result in messages.keys)
                    messages.get(result).toString()
                else
                    result

                enterValueText = binding.enterValueText.text.toString()
                enterRoundingText = binding.enterRoundingText.text.toString()
                resultText = result


                binding.resultText.text = text
            })
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("key_selected_group", selectedGroup)
        outState.putString("key_selected_to", selectedTo)
        outState.putString("key_selected_from", selectedFrom)
        outState.putString("key_value", enterValueText)
        outState.putString("key_rounding", enterRoundingText)
        outState.putString("key_result", resultText)
        outState.putStringArrayList("key_group_list", ArrayList(groupsList))
        outState.putStringArrayList("key_from_list", ArrayList(fromList))
    }

    fun _setInstantState(savedInstanceState: Bundle, binding: FragmentCalculatorBinding, viewModel: CalculatorViewModel) {
        selectedGroup = savedInstanceState.getString("key_selected_group").toString()
        selectedTo = savedInstanceState.getString("key_selected_to").toString()
        selectedFrom = savedInstanceState.getString("key_selected_from").toString()
        enterValueText = savedInstanceState.getString("key_value").toString()
        enterRoundingText = savedInstanceState.getString("key_rounding").toString()
        resultText = savedInstanceState.getString("key_result").toString()
        groupsList = savedInstanceState.getStringArrayList("key_group_list")?.toList() ?: listOf()
        fromList = savedInstanceState.getStringArrayList("key_from_list")?.toList() ?: listOf()

        binding.enterValueText.setText(enterValueText)
        binding.enterRoundingText.setText(enterRoundingText)
        binding.resultText.setText(resultText)

        val selectFromSpinner = binding.selectFromSpinner
        val selectToSpiner = binding.selectToSpinner

        viewModel.createAdapterForValuesSpinner(selectedGroup).observe(viewLifecycleOwner, Observer {
            fromList = it
            _createToAdapter(selectToSpiner, it)

            _createFromAdapter(selectFromSpinner, it)
        })

        binding.selectFromText.visibility = View.VISIBLE
        binding.selectFromSpinner.visibility = View.VISIBLE
        binding.selctToText.visibility = View.VISIBLE
        binding.selectToSpinner.visibility = View.VISIBLE

        binding.enterValueText.visibility = View.VISIBLE
        binding.enterRondingTextView.visibility = View.VISIBLE
        binding.enterRoundingText.visibility = View.VISIBLE

        binding.calculateButton.visibility = View.VISIBLE
        binding.resultText.visibility = View.VISIBLE
    }

    fun _createFromAdapter(adapter: Spinner, values: List<String>) {
        adapter.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        fromList = values
        adapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
            { selectedFrom = values[position] }
            override fun onNothingSelected(p0: AdapterView<*>?) {} }
        if (selectedFrom == "")
            adapter.setSelection(0)
        else
            adapter.setSelection(values.indexOf(selectedFrom))
    }

    fun _createToAdapter(adapter: Spinner, values: List<String>) {
        adapter.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        adapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
            { selectedTo = values[position] }
            override fun onNothingSelected(p0: AdapterView<*>?) {} }
        if (selectedTo == "")
            adapter.setSelection(0)
        else
            println(values.indexOf(selectedTo))
            adapter.setSelection(values.indexOf(selectedTo))
    }

    fun _createGroupSpinner(adapter: Spinner, values: List<String>) {
        adapter.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, values)
        groupsList = values
        adapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
            { selectedGroup = values[position] }
            override fun onNothingSelected(p0: AdapterView<*>?) {} }
        if (selectedGroup == "")
            adapter.setSelection(0)
        else
            adapter.setSelection(values.indexOf(selectedGroup))
    }
}