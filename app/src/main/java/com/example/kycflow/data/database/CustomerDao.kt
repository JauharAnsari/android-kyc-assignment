package com.example.kycflow.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers WHERE kycVerified = :isVerified")
    fun getCustomersByVerificationStatus(isVerified: Boolean): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers WHERE id = :id")
    fun getCustomerById(id: Int): Flow<CustomerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: List<CustomerEntity>)

    @Update
    suspend fun updateCustomer(customer: CustomerEntity)

    @Query("UPDATE customers SET kycVerified = 1, localSelfiePath = :imagePath WHERE id = :customerId")
    suspend fun verifyCustomerKyc(customerId: Int, imagePath: String)

    @Query("UPDATE customers SET bankName = :bankName, branchName = :branch, bankCity = :city, bankState = :state, micr = :micr WHERE id = :customerId")
    suspend fun updateBankDetails(customerId: Int, bankName: String, branch: String, city: String, state: String, micr: String)

    @Query("SELECT COUNT(*) FROM customers")
    suspend fun getCustomerCount(): Int
}
