package com.example.taskflow.view_modals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModal(val taskRepository: TaskRepository) : ViewModel() {


    fun getTaskList(listName:String,taskStatus: Boolean): LiveData<List<TaskLists>> {
        return taskRepository.getTasks(listName,taskStatus)
    }
    fun getAllTaskFromDB(taskStatus: Boolean):LiveData<List<TaskLists>>{
        return taskRepository.getAllTaskFromDB(taskStatus)
    }
   fun  getFinshedTasks(taskStatus:Boolean):LiveData<List<TaskLists>>{
       return taskRepository.getFinshedTasks(taskStatus)
   }
    fun getTasks(): LiveData<List<AddtoListItems>> {
        return taskRepository.getTaskList()

    }

    fun upDateTask(taskLists: TaskLists){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.upDateTask(taskLists)
        }
    }

    fun check(){
         viewModelScope.launch(Dispatchers.IO) {
             val isEmpty = taskRepository.getCount()
              if (isEmpty){
                  val defualtTaskList:MutableList<AddtoListItems> = mutableListOf()
                  val defaultList =  AddtoListItems(0,"Default")
                  val personal =  AddtoListItems(0,"Personal")
                  val shopping =  AddtoListItems(0,"Shopping")
                  val wishlist =  AddtoListItems(0,"Wishlist")
                  val work =  AddtoListItems(0,"Work")

                  defualtTaskList.add(defaultList)
                  defualtTaskList.add(personal)
                  defualtTaskList.add(shopping)
                  defualtTaskList.add(wishlist)
                  defualtTaskList.add(work)

                  for(i in defualtTaskList){
                      taskRepository.insertTaskName(i)
                  }
              }


         }
     }

}

