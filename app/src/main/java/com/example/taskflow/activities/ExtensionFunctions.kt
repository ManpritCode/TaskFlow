package com.example.taskflow.activities

import android.view.View

fun clickMthof(){
  var xyz = "m"
}

fun View.myclicks(block : ()-> Unit)=setOnClickListener{
  block.invoke()
}