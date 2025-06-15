package org.haidrrrry.auth.repository

import kotlinx.coroutines.flow.Flow
import org.haidrrrry.auth.classes.AuthAccount
import org.haidrrrry.auth.dao.AuthAccountDao


class AuthRepository(private val dao: AuthAccountDao) {

    fun getAllAccounts(): Flow<List<AuthAccount>> = dao.getAllAccounts()

    fun searchAccounts(query: String): Flow<List<AuthAccount>> = dao.searchAccounts(query)

    suspend fun getAccountById(id: String): AuthAccount? = dao.getAccountById(id)

    suspend fun insertAccount(account: AuthAccount) = dao.insertAccount(account)

    suspend fun insertAccounts(accounts: List<AuthAccount>) = dao.insertAccounts(accounts)

    suspend fun updateAccount(account: AuthAccount) = dao.updateAccount(account)

    suspend fun deleteAccount(account: AuthAccount) = dao.deleteAccount(account)

    suspend fun deleteAccountById(id: String) = dao.deleteAccountById(id)

    suspend fun deleteAllAccounts() = dao.deleteAllAccounts()

    suspend fun getAccountCount(): Int = dao.getAccountCount()
}