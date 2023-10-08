package com.example.taskflow.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.TaskDAO

@Database(entities = [TaskLists::class, AddtoListItems::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun daoInterface(): TaskDAO

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun geTaskDatabase(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TaskDatabase::class.java,
                        "TaskDB"
                    ).build()
                }

            }
            return INSTANCE!!
        }
    }

}