package com.example.taskflow.myInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists

@Dao
interface TaskDAO {
    @Insert
    suspend  fun insertTable(taskLists: TaskLists)


    @Insert
    suspend fun insertTableListName(addToListItems: AddtoListItems)

    @Query("SELECT * from AddtoListItems")
     fun getTaskList(): LiveData<List<AddtoListItems>>

     @Query("SELECT * from defaultList WHERE taskStatus = :taskStatus")
     fun getAllTaskFromDB(taskStatus: Boolean):LiveData<List<TaskLists>>

    @Query("SELECT * from  defaultList WHERE  listName = :listName AND taskStatus = :taskStatus")
     fun getTasks(listName:String ,taskStatus: Boolean): LiveData<List<TaskLists>>

    @Query("SELECT * from  defaultList WHERE  listName = :listName")
    fun getTasksByListName(listName:String): LiveData<List<TaskLists>>

    @Query("SELECT * from  defaultList WHERE  taskStatus = :taskStatus")
    fun finishedTask(taskStatus:Boolean): LiveData<List<TaskLists>>

    @Update
    suspend fun upDateTask(taskLists: TaskLists)

    @Delete
    suspend fun deleteTask(taskLists: TaskLists)

    @Query("DELETE FROM AddtoListItems WHERE listName = :listName")
     fun deleteListsByListNameFromDefaultList(listName: String)
    @Query("DELETE FROM defaultList WHERE listName = :listName")
     fun deleteTaskByListName(listName: String)

     @Transaction
     suspend fun deleteTasksAndListsByListName(listName: String){
         deleteTaskByListName(listName)
         deleteListsByListNameFromDefaultList(listName)
     }

    @Query("UPDATE AddtoListItems SET listName = :listName WHERE listName = :listName")
    fun updateListsByListNameFromDefaultList(listName: String)
    @Query("UPDATE defaultList SET listName = :listName WHERE listName = :listName")
    fun updateTaskByListName(listName: String)

    @Transaction
    suspend fun updateTasksAndListsByListName(listName: String){
        updateTaskByListName(listName)
        updateListsByListNameFromDefaultList(listName)
    }

    @Delete
    fun deleteTaskListName(addToListItems: AddtoListItems)

    @Query("SELECT COUNT(*) FROM AddtoListItems")
   suspend   fun getCount(): Int
}