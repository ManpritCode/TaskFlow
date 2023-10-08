package com.example.taskflow.view_modals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.models.AddtoListItems

class EditAndDeleteTasksViewModal( val taskRepository: TaskRepository):ViewModel() {

    fun getTasksListName():LiveData<List<AddtoListItems>>{
        return  taskRepository.getTaskList()
    }

}