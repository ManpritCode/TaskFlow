package com.example.taskflow.activities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.taskflow.Database.TaskDatabase
import com.example.taskflow.R
import com.example.taskflow.models.AddtoListItems
import com.example.taskflow.myInterfaces.Set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewListsDialLog(context: Context, private val appcontext: Context) :
    Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_list_dialogbox)
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.add_new_list_dialogbox)
        val cancelBtn = findViewById<TextView>(R.id.cancelButton)
        val addBtn = findViewById<TextView>(R.id.addButton)

        cancelBtn.setOnClickListener {
            dismiss()
        }
        // Add new List In Database
        addBtn.setOnClickListener {
            val listname = findViewById<EditText>(R.id.enterListName)
            val text = listname.text.toString()
            if (text == "") {
                Toast.makeText(context, "Please Enter List Name", Toast.LENGTH_SHORT).show()
            } else {
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