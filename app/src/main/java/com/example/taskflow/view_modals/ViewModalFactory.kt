package com.example.taskflow.view_modals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskflow.TaskFlowRepository.TaskRepository

class ViewModalFactory(val taskRepository: TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModal(taskRepository) as T
    }
}