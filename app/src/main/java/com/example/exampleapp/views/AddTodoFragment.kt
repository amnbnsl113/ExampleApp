package com.example.exampleapp.views

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.exampleapp.R
import com.example.exampleapp.databinding.AddTodoFragmentBinding
import com.example.exampleapp.vm.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddTodoFragment : Fragment(R.layout.add_todo_fragment) {
    private lateinit var binding: AddTodoFragmentBinding
    private val viewModel: AddTodoViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = AddTodoFragmentBinding.bind(view)
        binding.submitButton.setOnClickListener {
            viewModel.submitTodoItem()
        }
        binding.textInput.doOnTextChanged { text, _, _, _ -> viewModel.textChanged(text); }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uiEventsFlow.collect {
                when (it) {
                    AddTodoViewModel.AddTodoEvents.TODO_TEM_ADDED -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }


}