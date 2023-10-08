package com.example.taskflow.view_modals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNewTaskViewModal(private val taskRepository: TaskRepository) : ViewModel() {

    fun insertData(taskLists: TaskLists) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insertTask(taskLists)
        }
    }

    fun upDateTask(taskLists: TaskLists) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.upDateTask(taskLists)
        }
    }

    fun getTaskList():LiveData<List<AddtoListItems>>{
        return taskRepository.getTaskList()
    }

    fun deleteTask(taskLists: TaskLists){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(taskLists)
        }
    }
    fun deleteTaskName(addtoListItems: AddtoListItems){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTaskListName(addtoListItems)
        }
    }
}