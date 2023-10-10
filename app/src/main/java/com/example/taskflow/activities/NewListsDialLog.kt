package com.example.taskflow.activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.taskflow.Database.TaskDatabase
import com.example.taskflow.R
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.view_modals.EditAndDeleteTasksViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewListsDialLog(context: Context, private val appcontext: Context,
                      private val setDialLogType:Boolean,
                      var editAndDeleteTasksViewModal: EditAndDeleteTasksViewModal?=null,
                      var listName:String? =null
) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_list_dialogbox)
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.add_new_list_dialogbox)
        val dialogTitle = findViewById<TextView>(R.id.newListTitle)
        val cancelBtn = findViewById<TextView>(R.id.cancelButton)
        val addBtn = findViewById<TextView>(R.id.addButton)
        val listname = findViewById<EditText>(R.id.enterListName)

        if (setDialLogType){
            dialogTitle.text = "Edit Lists"
            listname.setText(listName)
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
        // Add new List In Database
        addBtn.setOnClickListener {
            val text = listname.text.toString()
            if (text == "") {
                Toast.makeText(context, "Please Enter List Name", Toast.LENGTH_SHORT).show()
            } else if(setDialLogType){
                editAndDeleteTasksViewModal?.updateTasksAndListsByListName("Manpreet")

                dismiss()
            }
            else {
                val listType: AddtoListItems = AddtoListItems(0, text)
                val dao = TaskDatabase.geTaskDatabase(appcontext).daoInterface()
                GlobalScope.launch(Dispatchers.IO) {
                    dao.insertTableListName(listType)
                    dismiss()
                }
            }


        }

    }

}