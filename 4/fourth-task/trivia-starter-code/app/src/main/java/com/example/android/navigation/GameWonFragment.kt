/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

//        передаем на экран значения в bundle
//        в bundle хранятся хэши переменных
//        requireArguments возвращает такой bundle
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "Num Questions: ${args.numQuestions}; Num Correct: ${args.numQuestions}", Toast.LENGTH_LONG).show()

//        настраиваем кнопку "Поделиться" на окне с победой в викторине
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

//        если на телефоне не будет ни одного приложения которое должно выполнить стороннюю активность
//        скроем кнопку поделиться
        val intent = getShareIntent()
        if (intent.resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> startActivity(getShareIntent())
        }
        return super.onOptionsItemSelected(item)
    }

//    функция для создания Intent
    private fun getShareIntent(): Intent {
//        формируем строку для отправки
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareString = "Num Questions: ${args.numQuestions}; Num Correct: ${args.numQuestions}"
//    создаем Intent для отправки
        val shareIntent = Intent(Intent.ACTION_SEND)
//    устанавливаем тип текст
        shareIntent.type = "text/plane"
//    передаем строку для отправки
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString)
        return shareIntent
    }
}
