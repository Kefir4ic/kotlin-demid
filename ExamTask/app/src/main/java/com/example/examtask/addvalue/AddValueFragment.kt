package com.example.examtask.addvalue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examtask.R
import com.example.examtask.database.CurrencyValueDatabase
import com.example.examtask.databinding.FragmentAddValueBinding

class AddValueFragment : Fragment() {

    private var enterNewValueGroupText = ""
    private var enterNewFromValueText = ""
    private var enterNewValueText = ""
    private var namesInGroup = listOf<String>()

    private lateinit var viewModel: AddValueViewModel
    private lateinit var binding: FragmentAddValueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val messages: Map<Int, String> = mapOf(
            0 to getString(R.string.successfully_added),
            1 to getString(R.string.empty_group_name),
            2 to getString(R.string.empty_value_name),
            3 to getString(R.string.empty_value),
            4 to getString(R.string.value_not_numeric),
            5 to getString(R.string.same_name))

        val binding = DataBindingUtil.inflate<FragmentAddValueBinding>(
            inflater, R.layout.fragment_add_value, container, false)

        if (savedInstanceState != null) {
            enterNewValueGroupText = savedInstanceState.getString("key_group_name").toString()
            enterNewFromValueText = savedInstanceState.getString("key_value_name").toString()
            enterNewValueText = savedInstanceState.getString("key_value").toString()
            binding.enterNewValueGroupText.setText(enterNewValueGroupText)
            binding.enterNewValueGroupText.setText(enterNewFromValueText)
            binding.enterNewValueText.setText(enterNewValueText)
        }

        val application = requireNotNull(this.activity).application
        val dao = CurrencyValueDatabase.getInstance(application).getCurrencyValueDatabaseDao()
        val viewModelFactory = AddValueViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddValueViewModel::class.java)

        binding.addValueButton.setOnClickListener {
            val currencyGroup = binding.enterNewValueGroupText
            val currencyName = binding.enterNewFromValueText
            val currencyValue = binding.enterNewValueText

            enterNewValueGroupText = binding.enterNewValueGroupText.text.toString()
            enterNewFromValueText = binding.enterNewFromValueText.text.toString()
            enterNewValueText = binding.enterNewValueText.text.toString()

            viewModel.createGroupValuesNames(enterNewValueGroupText).observe(viewLifecycleOwner, Observer {
                namesInGroup = it
                val code = viewModel.onAddValue(currencyGroup.text.toString(),
                    currencyName.text.toString(),
                    currencyValue.text.toString(),
                    namesInGroup)

                val message = messages.get(code)

                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, message, duration)
                toast.show()

                currencyGroup.text.clear()
                currencyName.text.clear()
                currencyValue.text.clear()
            })
        }


        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("key_group_name", enterNewValueGroupText)
        outState.putString("key_value_name", enterNewFromValueText)
        outState.putString("key_value", enterNewValueText)
    }
}