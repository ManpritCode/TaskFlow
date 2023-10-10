package com.example.taskflow.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.print.PrintAttributes.Margins
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import com.example.taskflow.R
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.models.TaskLists

class SpinnerCustomsdapter(
    var taskListsName: List<AddtoListItems>?=null,
    var context: Context,
    var homeString: String,
    var homeAdded: Boolean
) : BaseAdapter() {

    override fun getCount(): Int {
        if (homeAdded) {
            return taskListsName!!.size + 2

        } else {
            return taskListsName!!.size
        }
    }

    override fun getItem(position: Int): Any {
        if (homeAdded) {
            if (position == 0) {
                return homeString
            } else if (position == taskListsName!!.size + 1) {
                return "Finished"
            } else {
                return taskListsName!![position - 1]
            }
        } else {
            return ""
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(context).inflate(R.layout.spinner_toolbar_view, parent, false)
        val spinnerListName = view.findViewById<TextView>(R.id.spinnerListName)


        if (homeAdded) {
            if (position == 0) {
                spinnerListName.text = homeString
                spinnerListName.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))
            } else if (position == taskListsName!!.size+1) {
                spinnerListName.text = "Finished"
            } else {
                spinnerListName.text = taskListsName!![position - 1].listName
            }
        } else {
            spinnerListName.text = taskListsName!![position].listName
        }


        return view
    }

    @SuppressLint("ResourceAsColor")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(context).inflate(R.layout.spinner_toolbar_view, parent, false)

        val spinnerListName = view.findViewById<TextView>(R.id.spinnerListName)
        val rightDrawble = view.findViewById<ImageView>(R.id.leftDrawble)
        if (homeAdded) {
            if (position == 0) {
                spinnerListName.setTextColor(ContextCompat.getColor(context, R.color.white))
                spinnerListName.text = homeString
                spinnerListName.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))
                rightDrawble.setImageResource(R.drawable.baseline_home_24)
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))


            } else if (position == taskListsName!!.size +1) {
                spinnerListName.text = "Finished"
                spinnerListName.setTextColor(ContextCompat.getColor(context, R.color.white))
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))
                rightDrawble.setImageResource(R.drawable.baseline_check_box_rv)


            } else {
                spinnerListName.text = taskListsName!![position - 1].listName
                spinnerListName.setTextColor(ContextCompat.getColor(context, R.color.white))
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))
                rightDrawble.setImageResource(R.drawable.baseline_format_list_bulleted_24)
                spinnerListName.textSize = 25F
            }

        } else {
            spinnerListName.text = taskListsName!![position].listName
            spinnerListName.setTextColor(ContextCompat.getColor(context, R.color.white))
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.appblue))
            spinnerListName.textSize = 25F
        }

        return view
    }
}