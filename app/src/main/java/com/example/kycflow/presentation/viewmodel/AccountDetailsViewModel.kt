package com.example.kycflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kycflow.data.datasource.CustomerDataSource
import com.example.kycflow.data.database.CustomerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val customerDataSource: CustomerDataSource
) : ViewModel() {

    private val _customer = MutableStateFlow<CustomerEntity?>(null)
    val customer: StateFlow<CustomerEntity?> = _customer.asStateFlow()

    private val _isLoadingIfsc = MutableStateFlow(false)
    val isLoadingIfsc = _isLoadingIfsc.asStateFlow()

    fun loadCustomer(customerId: Int) {
        viewModelScope.launch {
            customerDataSource.getCustomerById(customerId).collectLatest { entity ->
                _customer.value = entity
                
                // If bank details aren't resolved yet, resolve them
                if (entity != null && entity.bankName == null && !_isLoadingIfsc.value) {
                    resolveIfsc(entity.id, entity.assignedIfsc)
                }
            }
        }
    }

    private fun resolveIfsc(customerId: Int, ifsc: String) {
        viewModelScope.launch {
            _isLoadingIfsc.value = true
            try {
                customerDataSource.resolveIfscAndCache(customerId, ifsc)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoadingIfsc.value = false
            }
        }
    }
}
