package com.example.kycflow.presentation.screen.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kycflow.R
import com.example.kycflow.domain.model.Customer
import com.example.kycflow.presentation.component.CustomerCard
import com.example.kycflow.presentation.viewmodel.AccountsViewModel

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onNavigateToDetails: (Int) -> Unit,
    viewModel: AccountsViewModel = hiltViewModel()
) {
    val customers by viewModel.customers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedChip by viewModel.selectedChip.collectAsState()
    
    val isLoading by viewModel.isLoading.collectAsState()
    val isPaginating by viewModel.isPaginating.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabs = listOf(stringResource(R.string.tab_verified), stringResource(R.string.tab_pending))
    
    // Filter customers by selected tab
    val filteredCustomers = customers.filter { customer ->
        if (selectedTabIndex == 0) customer.isVerified else !customer.isVerified
    }

    val chips = listOf("All", "Savings", "Current", "NRI")
    
    val gridState = rememberLazyGridState()

    // Observe scroll state for pagination
    LaunchedEffect(gridState, filteredCustomers.size) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                // Load more when user scrolls near the bottom (e.g., within 4 items of the end)
                if (lastIndex != null && filteredCustomers.isNotEmpty() && lastIndex >= filteredCustomers.size - 4) {
                    viewModel.loadMoreCustomers()
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Digital Bank") },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(stringResource(R.string.search_hint)) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )
            
            // Filter Chips
            ScrollableTabRow(
                selectedTabIndex = chips.indexOf(selectedChip),
                edgePadding = 16.dp,
                modifier = Modifier.padding(bottom = 8.dp),
                divider = {}
            ) {
                chips.forEachIndexed { index, chip ->
                    Tab(
                        selected = selectedChip == chip,
                        onClick = { viewModel.onChipSelected(chip) },
                        text = { Text(chip) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            
            Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                if (isLoading && customers.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (errorMessage != null && customers.isEmpty()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = errorMessage ?: "Error", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadInitialData() }) {
                            Text("Retry")
                        }
                    }
                } else if (filteredCustomers.isEmpty()) {
                    Text(
                        text = "No customers found.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = filteredCustomers,
                            key = { it.id }
                        ) { customer ->
                            CustomerCard(
                                customer = customer,
                                onClick = { onNavigateToDetails(customer.id) }
                            )
                        }
                        
                        if (isPaginating) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
