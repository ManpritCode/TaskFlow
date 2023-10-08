package com.example.taskflow.view_modals.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.taskflow.TaskFlowRepository.TaskRepository
import com.example.taskflow.view_modals.EditAndDeleteTasksViewModal

class EditAndDeleteTasksFactory( val taskRepository: TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EditAndDeleteTasksViewModal(taskRepository) as T
    }
}