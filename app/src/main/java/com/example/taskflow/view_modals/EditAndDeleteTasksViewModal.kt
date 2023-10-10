package com.example.taskflow.view_modals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAndDeleteTasksViewModal( val taskRepository: TaskRepository):ViewModel() {

    fun getTasksListName():LiveData<List<AddtoListItems>>{
        return  taskRepository.getTaskList()
    }
    fun getTasksByListName(listName:String): LiveData<List<TaskLists>>{
        return taskRepository.getTasksByListName(listName)
    }
    fun deleteTasksAnsListsByListName(listName: String){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTasksAndListsByListName(listName)
        }
    }
     fun updateTasksAndListsByListName(listName: String){
         viewModelScope.launch(Dispatchers.IO) {
             taskRepository.updateTasksAndListsByListName(listName)
         }
    }

}