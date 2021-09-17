package com.example.exampleapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exampleapp.R
import com.example.exampleapp.adapter.TodoListAdapter
import com.example.exampleapp.vm.TodoListViewModel
import com.example.exampleapp.databinding.FragmentTodoListBinding
import com.example.exampleapp.model.TodoItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListAdapter: TodoListAdapter
    private val viewModel: TodoListViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodoListBinding.bind(view)
        binding.viewModel = viewModel;
        binding.lifecycleOwner = this;
        todoListAdapter = TodoListAdapter(this::onItemClicked)

        viewModel.todoListLiveData.observe(viewLifecycleOwner, {
            binding.swiperefresh.isRefreshing = false
            if (!it.isLoading && !it.error.isError) {
                todoListAdapter.submitList(it.data);
            } else if (it.isLoading) {
                binding.swiperefresh.isRefreshing = true
            } else {
                binding.errorText.text = it.error.errorMessage
            }
        });

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.uiOperationsFlow.collect {
                when (it) {
                    TodoListViewModel.TodoViewEvent.ADD_TODO_EVENT -> {
                        navigateToAddTodoFragment()
                    }
                }
            }
        }

        binding.recyclerView.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun navigateToAddTodoFragment() {
        val action = TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment()
        view?.findNavController()?.navigate(action)
    }

    fun onItemClicked(value: TodoItem) {
        Snackbar.make(binding.root, value.title, Snackbar.LENGTH_LONG).show();
    }
}