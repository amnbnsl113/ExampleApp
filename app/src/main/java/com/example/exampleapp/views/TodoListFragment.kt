package com.example.exampleapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exampleapp.adapter.TodoListAdapter
import com.example.exampleapp.vm.TodoListViewModel
import com.example.exampleapp.databinding.FragmentTodoListBinding
import com.example.exampleapp.model.UserData

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var viewModel: TodoListViewModel

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
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        todoListAdapter = TodoListAdapter(ArrayList(), this::onItemClicked)

        viewModel.stateModelLiveData.observe(viewLifecycleOwner, {
            if (!it.isLoading && !it.isError) {
                binding.swiperefresh.isRefreshing = false
                todoListAdapter.setValues(it.data?.list);
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
        Log.e("pass", value.first_name);
    }
}