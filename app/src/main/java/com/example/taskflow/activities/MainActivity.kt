package com.example.taskflow.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.taskflow.Database.TaskDatabase
import com.example.taskflow.R
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.adapters.SpinnerCustomsdapter
import com.example.taskflow.adapters.TaskListRecyclerViewAdapter
import com.example.taskflow.databinding.ActivityMainBinding
import com.example.taskflow.models.TaskLists
import com.example.taskflow.myInterfaces.FinishedTasksInterface
import com.example.taskflow.view_modals.MainViewModal
import com.example.taskflow.view_modals.factories.ViewModalFactory
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity(), FinishedTasksInterface {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var mainViewModal: MainViewModal
    var allListValue = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = TaskDatabase.geTaskDatabase(this).daoInterface()
        val repository = TaskRepository(dao)
        mainViewModal =
            ViewModelProvider(this, ViewModalFactory(repository))[MainViewModal::class.java]
        setContentView(binding.root)
        setDefaultListIntoTable()
        binding.spinner.setSelection(1)
        topBarDropDown()

        val materialToolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        materialToolbar.setOnClickListener {
            Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
        }
        materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.taskLists -> {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            TaskListsEditAndDeleteActivity::class.java
                        )
                    )
                    true
                }

                else -> false
            }
        }

        binding.addTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("view", 1)
            startActivity(intent)
            finish()
        }
    }

    private fun setDefaultListIntoTable() {
        mainViewModal.check()
    }

    private fun topBarDropDown() {

        mainViewModal.getTasks().observe(this, Observer { it ->
            binding.spinner.adapter = SpinnerCustomsdapter(it, this, "All Lists", true)
            binding.spinner.onItemSelectedListener =
                object : AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        var index: Int? = null
                        if (p2 == 0) {
                            allListValue = 1
                        }
                        if (p2 > 1) {
                            allListValue = 3
                        }
                        if (p2 == it.size + 1) {
                            allListValue = 2
                        }

                        if (p2 == 0) {
                            mainViewModal.getAllTaskFromDB(false)
                                .observe(this@MainActivity, Observer {
                                    if (allListValue == 1) {
                                        binding.TaskListRecyclerView.adapter =
                                            TaskListRecyclerViewAdapter(
                                                this@MainActivity, it, this@MainActivity
                                            )
                                    }
                                })
                        } else if (p2 > 1 && p2 < it.size + 1) {
                            index = p2 - 1
                            val clickedListName = it[index!!].listName
                            mainViewModal.getTaskList(clickedListName, false)
                                .observe(this@MainActivity, Observer {
                                    if (allListValue == 3) {
                                        binding.TaskListRecyclerView.adapter =
                                            TaskListRecyclerViewAdapter(
                                                this@MainActivity, it, this@MainActivity
                                            )
                                    }
                                })
                        } else {
                            mainViewModal.getFinshedTasks(true)
                                .observe(this@MainActivity, Observer {
                                    if (allListValue == 2) {

                                        binding.TaskListRecyclerView.adapter =
                                            TaskListRecyclerViewAdapter(
                                                this@MainActivity,
                                                it,
                                                this@MainActivity
                                            )
                                    }
                                })
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
                }
        }
        )

        val sp = findViewById<Spinner>(R.id.spinner)
        sp.dropDownVerticalOffset = 100
        sp.dropDownWidth = 500
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.more_side_menu, menu)
        return true
    }

    override fun updateStatasFinishedTask(taskLists: TaskLists, selectionTypeValue: String) {
        mainViewModal.upDateTask(taskLists)

    }


}
