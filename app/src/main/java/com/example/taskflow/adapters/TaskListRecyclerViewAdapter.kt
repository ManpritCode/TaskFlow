package com.example.taskflow.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskflow.R
import com.example.taskflow.activities.AddTaskActivity
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.FinishedTasksInterface

class TaskListRecyclerViewAdapter( val context: Context,val taskslist:List<TaskLists>,val  finishedTasksInterface: FinishedTasksInterface): RecyclerView.Adapter<TaskListRecyclerViewAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_list_recycler_view,parent ,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
         return  taskslist.size
    }
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val text = taskslist[position]
        holder.taskName.text = text.taskName
        holder.taskDate.text = text.taskDate
        holder.listName.text = text.listName
        Log.d("chekdata",taskslist.toString())

        if (text.taskStatus == true){
            holder.checkBox.isChecked =  true
        }else{
            holder.checkBox.isChecked =  false
        }
        holder.itemView.setOnClickListener {

            val intent = Intent(context ,AddTaskActivity::class.java)
            intent.putExtra("taskName",text.taskName)
            intent.putExtra("taskDate",text.taskDate)
            intent.putExtra("listName",text.listName)
            intent.putExtra("id",text.id,)
            intent.putExtra("listId",text.listId)
            context.startActivity(intent)
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val updateTask = TaskLists(text.id,text.taskName,text.taskDate,text.listName,text.listId,true)
                finishedTasksInterface.updateStatasFinishedTask(updateTask, text.listName)
            }else{
                val updateTask = TaskLists(text.id,text.taskName,text.taskDate,text.listName,text.listId,false)
                finishedTasksInterface.updateStatasFinishedTask(updateTask, text.listName)
            }
        }
    }
    class viewHolder(item:View):ViewHolder(item){
        val taskName = item.findViewById<TextView>(R.id.taskName)
        val taskDate = item.findViewById<TextView>(R.id.taskDate)
        val checkBox = item.findViewById<CheckBox>(R.id.checkBox)
        val listName = item.findViewById<TextView>(R.id.listName)
    }
}