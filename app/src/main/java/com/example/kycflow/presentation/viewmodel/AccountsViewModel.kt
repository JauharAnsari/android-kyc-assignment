package com.example.kycflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kycflow.data.datasource.CustomerDataSource
import com.example.kycflow.data.mapper.toDomainModel
import com.example.kycflow.domain.model.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class)
@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val customerDataSource: CustomerDataSource
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    
    private val _selectedChip = MutableStateFlow("All")
    val selectedChip = _selectedChip.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating = _isPaginating.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                customerDataSource.syncCustomersIfNeeded()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load data. Please check your connection."
            } finally {
                _isLoading.value = false
            }
        }
    }

    val customers: StateFlow<List<Customer>> = combine(
        customerDataSource.getAllCustomers(),
        _searchQuery.debounce(200),
        _selectedChip
    ) { entities, query, chip ->
        entities
            .map { it.toDomainModel() }
            .filter {
                val matchesSearch = it.fullName.contains(query, ignoreCase = true) ||
                        it.accountNumber.contains(query, ignoreCase = true)
                val matchesChip = if (chip == "All") true else it.accountType == chip
                matchesSearch && matchesChip
            }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    fun onChipSelected(chip: String) {
        _selectedChip.value = chip
    }

    private var isEndOfPagination = false

    fun loadMoreCustomers() {
        if (_searchQuery.value.isEmpty() && _selectedChip.value == "All" && !_isPaginating.value && !isEndOfPagination) {
            viewModelScope.launch {
                _isPaginating.value = true
                try {
                    val hasMore = customerDataSource.loadMoreCustomers()
                    if (!hasMore) {
                        isEndOfPagination = true
                    }
                } catch (e: Exception) {
                    // Ignore pagination errors for simplicity or show a toast
                } finally {
                    _isPaginating.value = false
                }
            }
        }
    }
}
