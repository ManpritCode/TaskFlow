package com.example.taskflow.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.taskflow.Database.TaskDatabase
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.adapters.SpinnerCustomsdapter
import com.example.taskflow.databinding.ActivityAddTaskBinding
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.Set
import com.example.taskflow.view_modals.AddNewTaskFactory
import com.example.taskflow.view_modals.AddNewTaskViewModal
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() ,Set{
    private val binding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }
    var selectedListName: String? = null
    var listId: Int? = null

    private var addNewTaskViewModal: AddNewTaskViewModal? = null
    private var listName: String? = null
    private var index:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var shareBtnAndDelete = intent.getIntExtra("view", 0)

        val taskDao = TaskDatabase.geTaskDatabase(this).daoInterface()
        val taskRepository = TaskRepository(taskDao)
        addNewTaskViewModal = ViewModelProvider(this, AddNewTaskFactory(taskRepository)
        )[AddNewTaskViewModal::class.java]


        if (shareBtnAndDelete == 1) {
            binding.shareBtn.visibility = View.GONE
            binding.deleteBtn.visibility = View.GONE
            binding.newTaskTitle.visibility = View.VISIBLE
        } else {
            binding.shareBtn.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.VISIBLE
            binding.newTaskTitle.visibility = View.GONE
            updatedata()
        }
       addToListSetAdapter()
        setContentView(binding.root)

        binding.newListCreate.setOnClickListener {
            val newListsDialLog = NewListsDialLog(this, applicationContext,this)
            newListsDialLog.show()
        }

        binding.backarrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            onBackPressed()
        }


        binding.addListIntoTable.setOnClickListener {
            if (shareBtnAndDelete == 1) {
                addTaskIntoTable()
            } else {
                upDateTask()
            }
        }
        //date Picker from calender
        binding.datePicker.setOnClickListener {
            datePickerDialog()
        }
        binding.shareBtn.setOnClickListener {
            shareTaskToOtherApp()
        }
        binding.deleteBtn.setOnClickListener {
           deleteTaskFromApp()
        }

    }
          //deleteTask from the  app
    private fun deleteTaskFromApp() {
        val taskName = binding.enterTaskHere.text.toString()
        val taskDate = binding.datePicker.text.toString()
        val taskId  = intent.getIntExtra("id",0)
        val task1 = selectedListName?.let { TaskLists(taskId,taskName,taskDate, it,) }
        if (task1 != null) {
            addNewTaskViewModal?.deleteTask(task1)
        }
        finish()
    }
// share task to other apps
    private fun shareTaskToOtherApp() {
        val taskName = intent.getStringExtra("taskName")
        val taskDate = intent.getStringExtra("taskDate")
        val transferString = "$taskName ($taskDate)"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type ="text/plain"
            putExtra(Intent.EXTRA_TEXT,transferString)

        }
        startActivity(Intent.createChooser(sendIntent,"Share Via"))
    }

    //Date picker from calendar
    private fun datePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog and set its listener
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Do something with the selected date
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.datePicker.text = selectedDate
            },
            year,
            month,
            day
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    // setEditValue
    private fun updatedata() {
        val taskName = intent.getStringExtra("taskName")
        val taskDate = intent.getStringExtra("taskDate")
        listName = intent.getStringExtra("listName")
        binding.enterTaskHere.setText(taskName)
        binding.datePicker.text = taskDate
    }

    // Create a new Task into Database table
    private fun addTaskIntoTable() {
        val taskName = binding.enterTaskHere.text.toString()
        val taskDate = binding.datePicker.text.toString()
        if (taskName == "" || taskDate == "" || selectedListName == "") {
            Toast.makeText(this, "Please Enter Task", Toast.LENGTH_SHORT).show()
        }else{
            val task1 = selectedListName?.let { TaskLists(0, taskName, taskDate, it,listId,false) }
            addNewTaskViewModal?.insertData(task1!!)
            startActivity(Intent(this@AddTaskActivity,MainActivity::class.java))
        }
    }
      //UpdateTaskIntoTable
    private fun upDateTask() {
          val taskName = binding.enterTaskHere.text.toString()
          val taskDate = binding.datePicker.text.toString()
          val taskId  = intent.getIntExtra("id",0)
          val task1 = selectedListName?.let { TaskLists(taskId,taskName,taskDate, it,listId,false) }
          if (task1 != null) {
              addNewTaskViewModal?.upDateTask(task1)
          }
          startActivity(Intent(this@AddTaskActivity,MainActivity::class.java))
      }


    //  Move to  previous Activity
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // Set list into Spinner adapter
    private fun addToListSetAdapter() {
         addNewTaskViewModal?.getTaskList()?.observe(this, Observer {
             binding.addtoListSpinner.adapter = SpinnerCustomsdapter(it,this,"Home", false)
             binding.addtoListSpinner.onItemSelectedListener =
                 object : AdapterView.OnItemClickListener,
                     AdapterView.OnItemSelectedListener {
                     override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                         selectedListName = it[p2].listName
                         listId=it[p2].id

                     }
                     override fun onNothingSelected(p0: AdapterView<*>?) {}
                     override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
                 }
         })

    }

    override fun setselection() {
        addNewTaskViewModal?.getTaskList()?.observe(this, Observer {
            binding.addtoListSpinner.setSelection(it.size)
        })
    }


}

