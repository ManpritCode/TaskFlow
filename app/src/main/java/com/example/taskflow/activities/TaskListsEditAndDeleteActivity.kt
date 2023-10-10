package com.example.taskflow.activities

import android.content.Intent
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
import com.example.taskflow.models.ListNameAndTasksCount
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.DeleteTasksAndListsByListName
import com.example.taskflow.view_modals.EditAndDeleteTasksViewModal
import com.example.taskflow.view_modals.factories.EditAndDeleteTasksFactory
import com.google.gson.Gson

class TaskListsEditAndDeleteActivity : AppCompatActivity(), DeleteTasksAndListsByListName {

    private val binding by lazy {
        ActivityTaskListsEditAndDeleteBinding.inflate(layoutInflater)
    }
    var observerHandel = 0
    private lateinit var editAndDeleteTasksViewModal: EditAndDeleteTasksViewModal
    var countZero = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
     binding.backArrow.setOnClickListener {
         startActivity(Intent(this@TaskListsEditAndDeleteActivity,MainActivity::class.java))
         finish()
     }
        val dao = TaskDatabase.geTaskDatabase(this).daoInterface()
        val taskRepository = TaskRepository(dao)
        editAndDeleteTasksViewModal =
            ViewModelProvider(this, EditAndDeleteTasksFactory(taskRepository)).get(
                EditAndDeleteTasksViewModal::class.java
            )
        editAndDeleteTasksViewModal.getTasksListName().observe(this, Observer { it ->
            var tasksListNameAndCount: ArrayList<ListNameAndTasksCount> = arrayListOf()
            val alwaysOnTopItem = ListNameAndTasksCount(it[0].listName, 0)
            tasksListNameAndCount.add(alwaysOnTopItem)
            for (i in it) {
                editAndDeleteTasksViewModal.getTasksByListName(i.listName).observe(this, Observer {
                    countZero = 0
                    for (j in it){
                        if (j.taskStatus == false){
                             countZero += 1
                        }
                    }
                    if (i.listName == "Default"){
                        tasksListNameAndCount[0].listname = i.listName
                        tasksListNameAndCount[0].taskSize = countZero
                    }else{
                        var list = countZero?.let { it1 -> ListNameAndTasksCount(i.listName, it1) }
                        if (list != null) {
                            tasksListNameAndCount.add(list)
                        }
                    }
                        recyclerViewSet(tasksListNameAndCount)
                })
            }
        }
        )
    }
    fun recyclerViewSet(list:ArrayList<ListNameAndTasksCount>){
        binding.recyclerViewEditAndDelete.adapter =
            EditAndDeletTaskListRvAdapter(this, list, this)
        binding.recyclerViewEditAndDelete.adapter?.notifyDataSetChanged()
    }

    override fun deleteTasksAndListsByListName(listName: String) {
        editAndDeleteTasksViewModal.deleteTasksAnsListsByListName(listName)
    }

    override fun updateTasksAndListsByListName(listName: String) {
        val newListsDialLog = NewListsDialLog(this, applicationContext,true,editAndDeleteTasksViewModal,listName)
        newListsDialLog.show()
    }
}