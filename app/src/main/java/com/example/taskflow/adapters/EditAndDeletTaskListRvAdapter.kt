package com.example.taskflow.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskflow.R
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists
import com.google.gson.Gson

class EditAndDeletTaskListRvAdapter(val context: Context, private val taskList:List<AddtoListItems>): RecyclerView.Adapter<EditAndDeletTaskListRvAdapter.viewHolder>() {
 init {

     Log.d("sdnfjmsda", Gson().toJson(taskList))
 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_and_delete_recycler_layout ,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
         val text = taskList[position]
        holder.listName.text = text.listName
        holder.tasksCount.text = text.listName

    }
    class viewHolder(itemview:View):ViewHolder(itemview){
      val listName = itemview.findViewById<TextView>(R.id.listNameEAndD)
        val tasksCount =itemview.findViewById<TextView>(R.id.tasksCountEAndD)

    }
}