package com.example.taskflow.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "defaultList")
data class TaskLists(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val taskName:String,
    val taskDate: String ,
    val listName:String,
    val listId:Int?=null,
    val taskStatus:Boolean?=null
)
