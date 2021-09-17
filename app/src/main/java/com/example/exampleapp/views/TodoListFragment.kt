package com.example.exampleapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exampleapp.adapter.TodoListAdapter
import com.example.exampleapp.vm.TodoListViewModel
import com.example.exampleapp.databinding.FragmentTodoListBinding
import com.example.exampleapp.model.UserData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListAdapter: TodoListAdapter
    private val viewModel: TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel;
        binding.lifecycleOwner = this;

        todoListAdapter = TodoListAdapter(this::onItemClicked)

        viewModel.refreshData()
        viewModel.stateModelLiveData.observe(viewLifecycleOwner, {
            binding.swiperefresh.isRefreshing = false
            if (!it.isLoading && !it.isError) {
                todoListAdapter.submitList(it.data?.list);
            } else if (it.isLoading) {
                binding.swiperefresh.isRefreshing = true
            } else {
                binding.errorText.text = it.errorMessage
            }
        });

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.recyclerView.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    fun onItemClicked(value: UserData) {
        Snackbar.make(binding.root, value.first_name, Snackbar.LENGTH_LONG).show();
    }
}