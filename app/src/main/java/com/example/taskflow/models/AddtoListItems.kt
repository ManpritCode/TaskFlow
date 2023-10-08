package com.example.taskflow.models

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddtoListItems(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var listName: String)
