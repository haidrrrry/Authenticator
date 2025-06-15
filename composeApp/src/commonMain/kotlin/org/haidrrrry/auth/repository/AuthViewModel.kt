package org.haidrrrry.auth.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.haidrrrry.auth.classes.AuthAccount

import androidx.compose.ui.graphics.Color

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Get accounts based on search query
    val accounts: Flow<List<AuthAccount>> = combine(
        repository.getAllAccounts(),
        _searchQuery
    ) { accounts, query ->
        if (query.isBlank()) {
            accounts
        } else {
            accounts.filter { account ->
                account.serviceName.contains(query, ignoreCase = true) ||
                        account.accountName.contains(query, ignoreCase = true)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addAccount(account: AuthAccount) {
        viewModelScope.launch {
            repository.insertAccount(account)
        }
    }

    fun updateAccount(account: AuthAccount) {
        viewModelScope.launch {
            repository.updateAccount(account)
        }
    }

    fun deleteAccount(account: AuthAccount) {
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }

    fun deleteAccountById(id: String) {
        viewModelScope.launch {
            repository.deleteAccountById(id)
        }
    }

    // Initialize with sample data
    fun initializeSampleData() {
        viewModelScope.launch {
            val count = repository.getAccountCount()
            if (count == 0) {
                val sampleAccounts = listOf(
                    AuthAccount("1", "Google", "john.doe@gmail.com", "JBSWY3DPEHPK3PXP", Color(0xFF4285F4), "🔍"),
                    AuthAccount("2", "GitHub", "johndoe", "HXDMVJECJJWSRB3HWIZR4IFUGFTMXBOZ", Color(0xFF24292e), "🐙"),
                    AuthAccount("3", "Discord", "JohnDoe#1234", "GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ", Color(0xFF5865F2), "🎮"),
                    AuthAccount("4", "Dropbox", "john.doe@gmail.com", "MFRGG2LTMRPWSZTS", Color(0xFF0061FF), "📦"),
                    AuthAccount("5", "Microsoft", "john.doe@outlook.com", "KRMVATZTJFZUC53FONXW2ZJB", Color(0xFF00BCF2), "Ⓜ️"),
                    AuthAccount("6", "Amazon", "johndoe", "MFXHIIDBMJYGC5LS", Color(0xFFFF9900), "📦")
                )
                repository.insertAccounts(sampleAccounts)
            }
        }
    }
}