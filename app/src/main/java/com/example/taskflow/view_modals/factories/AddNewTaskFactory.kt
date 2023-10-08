package com.example.taskflow.view_modals.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.view_modals.AddNewTaskViewModal

class AddNewTaskFactory(val taskRepository: TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddNewTaskViewModal(taskRepository) as T
    }
}