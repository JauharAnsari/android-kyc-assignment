package com.example.kycflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kycflow.data.datasource.CustomerDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val customerDataSource: CustomerDataSource
) : ViewModel() {

    fun updateKycStatus(customerId: Int, selfiePath: String) {
        viewModelScope.launch {
            try {
                customerDataSource.updateKycStatus(customerId, selfiePath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
