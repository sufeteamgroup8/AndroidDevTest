package com.systemvx.androiddevtest.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.data.model.OrderDetail

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class CommentSendViewModelFactory(val data: OrderDetail) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentSendViewModel::class.java)) {
            return CommentSendViewModel(orderData = data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}