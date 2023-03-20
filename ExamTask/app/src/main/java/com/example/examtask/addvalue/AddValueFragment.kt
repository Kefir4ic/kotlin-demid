package com.example.examtask.addvalue

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.examtask.R
import com.example.examtask.database.CurrencyValueDatabase
import com.example.examtask.databinding.FragmentAddValueBinding

class AddValueFragment : Fragment() {

    private lateinit var viewModel: AddValueViewModel
    private lateinit var binding: FragmentAddValueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAddValueBinding>(
            inflater, R.layout.fragment_add_value, container, false)

        val application = requireNotNull(this.activity).application
        val dao = CurrencyValueDatabase.getInstance(application).getCurrencyValueDatabaseDao()
        val viewModelFactory = AddValueViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddValueViewModel::class.java)

        viewModel.checkAll()

        binding.addValueButton.setOnClickListener {
            addValue(application.applicationContext)
        }


        return binding.root
    }

    fun addValue(context: Context){
        val currencyGroup = binding.enterNewValueGroupText
        val currencyName = binding.enterNewFromValueText
        val currencyValue = binding.enterNewValueText

        val message = viewModel.onAddValue(currencyGroup.text.toString(),
            currencyName.text.toString(),
            currencyValue.text.toString())

        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, message, duration)
        toast.show()

        currencyGroup.text.clear()
        currencyName.text.clear()
        currencyValue.text.clear()
    }

}