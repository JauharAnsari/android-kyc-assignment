package com.example.kycflow.data.datasource

import com.example.kycflow.data.api.CustomerApi
import com.example.kycflow.data.api.IfscApi
import com.example.kycflow.data.database.CustomerDao
import com.example.kycflow.data.database.CustomerEntity
import com.example.kycflow.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class CustomerDataSource @Inject constructor(
    private val customerApi: CustomerApi,
    private val ifscApi: IfscApi,
    private val customerDao: CustomerDao
) {
    private val CACHE_EXPIRATION_MS = 60 * 60 * 1000 // 1 hour

    private val validIfscs = listOf(
        "HDFC0CAGSBK", "SBIN0000001", "ICIC0000001", "PUNB0244200", "UTIB0000001"
    )

    fun getAllCustomers(): Flow<List<CustomerEntity>> = customerDao.getAllCustomers()

    fun getCustomerById(id: Int): Flow<CustomerEntity?> = customerDao.getCustomerById(id)

    suspend fun syncCustomersIfNeeded() {
        try {
            val count = customerDao.getCustomerCount()
            if (count == 0) {
                // Initial load
                fetchAndCacheCustomers()
            } else {
                // In a full implementation, you would check lastUpdated of the first item
                // For this assignment, checking count > 0 is a good start, but we can do forced sync
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun forceSyncCustomers() {
        try {
            fetchAndCacheCustomers()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private suspend fun fetchAndCacheCustomers(skip: Int = 0): Int {
        val response = customerApi.getUsers(limit = 20, skip = skip)
        if (response.users.isEmpty()) return 0
        
        val entities = response.users.map { user ->
            val randomBalance = Random.nextDouble(500.0, 150000.0)
            val randomIfsc = validIfscs.random()
            
            // Generate some random account types for the chips (All, Savings, Current, NRI)
            val accountTypes = listOf("Savings", "Current", "NRI", "Savings", "Savings")
            
            val entity = user.toEntity(assignedIfsc = randomIfsc, balance = randomBalance)
            entity.copy(cardType = accountTypes.random()) // Override for chip filtering assignment
        }
        customerDao.insertCustomers(entities)
        return entities.size
    }

    suspend fun loadMoreCustomers(): Boolean {
        try {
            val currentCount = customerDao.getCustomerCount()
            val count = fetchAndCacheCustomers(skip = currentCount)
            return count > 0
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun resolveIfscAndCache(customerId: Int, ifsc: String) {
        try {
            val response = ifscApi.getBankDetails(ifsc)
            customerDao.updateBankDetails(
                customerId = customerId,
                bankName = response.bank,
                branch = response.branch,
                city = response.city,
                state = response.state,
                micr = response.micr
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
    
    suspend fun updateKycStatus(customerId: Int, selfiePath: String) {
        customerDao.verifyCustomerKyc(customerId, selfiePath)
    }
}
