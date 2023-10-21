package com.example.taskflow.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskflow.R
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.ListNameAndTasksCount
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.DeleteTasksAndListsByListName
import com.google.gson.Gson

class EditAndDeletTaskListRvAdapter(val context: Context, private val taskList:ArrayList<ListNameAndTasksCount>,val deleteTasksAndListsByListName: DeleteTasksAndListsByListName): RecyclerView.Adapter<EditAndDeletTaskListRvAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_and_delete_recycler_layout ,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
         val text = taskList[position]
        holder.listName.text = text.listname
        holder.tasksCount.text = "Tasks :${text.taskSize}"
        if (text.listname == "Default"){
            holder.editBtn.visibility = View.GONE
            holder.deletBtn.visibility = View.GONE
        }
        holder.deletBtn.setOnClickListener {
            deleteTasksAndListsByListName.deleteTasksAndListsByListName(text.listname)
            notifyDataSetChanged()
        }
        holder.editBtn.setOnClickListener {
            deleteTasksAndListsByListName.updateTasksAndListsByListName(text.listname)
            notifyDataSetChanged()
        }
    }
    class viewHolder(itemview:View):ViewHolder(itemview){
      val listName = itemview.findViewById<TextView>(R.id.listNameEAndD)
        val tasksCount =itemview.findViewById<TextView>(R.id.tasksCountEAndD)
        val deletBtn = itemview.findViewById<ImageView>(R.id.deleteTaskList)
        val editBtn = itemview.findViewById<ImageView>(R.id.editTaskListName)
    }
}