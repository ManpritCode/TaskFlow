package com.example.taskflow.myInterfaces

import com.example.taskflow.models.TaskLists

interface FinishedTasksInterface {
   fun updateStatasFinishedTask(updateTask:TaskLists, selectionTypeValue:String)
}