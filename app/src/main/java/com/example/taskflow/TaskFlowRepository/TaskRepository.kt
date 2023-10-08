package com.example.taskflow.TaskFlowRepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.TaskDAO

class TaskRepository( val taskDAO: TaskDAO) {

    suspend fun insertTask(taskLists: TaskLists){
        taskDAO.insertTable(taskLists)
    }

     fun getTaskList():LiveData<List<AddtoListItems>>{
        return taskDAO.getTaskList()
    }

    fun getAllTaskFromDB(taskStatus: Boolean):LiveData<List<TaskLists>>{
        return taskDAO.getAllTaskFromDB(taskStatus)
    }

     fun getTasks(listName:String ,taskStatus: Boolean):LiveData<List<TaskLists>>{
        return taskDAO.getTasks(listName ,taskStatus)
    }
    fun getFinshedTasks(taskStatus:Boolean):LiveData<List<TaskLists>>{
        return taskDAO.finishedTask(taskStatus)
    }
    suspend fun upDateTask(taskLists: TaskLists){
        taskDAO.upDateTask(taskLists)

    }

    suspend fun deleteTask(taskLists: TaskLists){
        taskDAO.deleteTask(taskLists)
    }
    suspend fun deleteTaskListName(addtoListItems: AddtoListItems){
        taskDAO.deleteTaskListName(addtoListItems)
    }
  suspend   fun getCount():Boolean{
         val rowcount =taskDAO.getCount()
        return rowcount == 0
    }
    suspend fun insertTaskName(addtoListItems: AddtoListItems){
        taskDAO.insertTableListName(addtoListItems)
    }


}