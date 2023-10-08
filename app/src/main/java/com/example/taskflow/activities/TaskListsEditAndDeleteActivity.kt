package com.example.taskflow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.taskflow.Database.TaskDatabase
import com.example.taskflow.R
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.adapters.EditAndDeletTaskListRvAdapter
import com.example.taskflow.databinding.ActivityTaskListsEditAndDeleteBinding
import com.example.taskflow.view_modals.EditAndDeleteTasksViewModal
import com.example.taskflow.view_modals.factories.EditAndDeleteTasksFactory
import com.google.gson.Gson

class TaskListsEditAndDeleteActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityTaskListsEditAndDeleteBinding.inflate(layoutInflater)
    }

    lateinit var editAndDeleteTasksViewModal: EditAndDeleteTasksViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val dao = TaskDatabase.geTaskDatabase(this).daoInterface()
         val taskRepository = TaskRepository(dao)
        editAndDeleteTasksViewModal = ViewModelProvider(this,EditAndDeleteTasksFactory(taskRepository)).get(EditAndDeleteTasksViewModal::class.java)

        editAndDeleteTasksViewModal.getTasksListName().observe(this, Observer {
            Log.d("dfkmvkmd",Gson().toJson(it))
            binding.recyclerViewEditAndDelete.adapter = EditAndDeletTaskListRvAdapter(this,it)
        })


    }
}