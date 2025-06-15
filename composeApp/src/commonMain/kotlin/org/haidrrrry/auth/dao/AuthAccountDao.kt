package org.haidrrrry.auth.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.haidrrrry.auth.classes.AuthAccount

@Dao
interface AuthAccountDao {
    @Query("SELECT * FROM auth_accounts ORDER BY serviceName ASC")
    fun getAllAccounts(): Flow<List<AuthAccount>>

    @Query("SELECT * FROM auth_accounts WHERE id = :id")
    suspend fun getAccountById(id: String): AuthAccount?

    @Query("SELECT * FROM auth_accounts WHERE serviceName LIKE '%' || :searchQuery || '%' OR accountName LIKE '%' || :searchQuery || '%'")
    fun searchAccounts(searchQuery: String): Flow<List<AuthAccount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AuthAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AuthAccount>)

    @Update
    suspend fun updateAccount(account: AuthAccount)

    @Delete
    suspend fun deleteAccount(account: AuthAccount)

    @Query("DELETE FROM auth_accounts WHERE id = :id")
    suspend fun deleteAccountById(id: String)

    @Query("DELETE FROM auth_accounts")
    suspend fun deleteAllAccounts()

    @Query("SELECT COUNT(*) FROM auth_accounts")
    suspend fun getAccountCount(): Int
}