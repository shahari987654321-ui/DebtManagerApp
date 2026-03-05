package com.example.debtmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.debtmanager.data.Debt
import com.example.debtmanager.data.DebtDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DebtViewModel(private val debtDao: DebtDao) : ViewModel() {

    val allDebts: StateFlow<List<Debt>> = debtDao.getAllDebts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalDebtAmount: StateFlow<Double> = debtDao.getTotalDebtAmount()
        .map { it ?: 0.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addDebt(shopName: String, amount: Double, date: Long) {
        viewModelScope.launch {
            debtDao.insertDebt(Debt(shopName = shopName, amount = amount, date = date))
        }
    }

    fun payDebt(debt: Debt, amountToPay: Double) {
        viewModelScope.launch {
            if (amountToPay >= debt.amount) {
                debtDao.deleteDebt(debt)
            } else {
                val updatedDebt = debt.copy(amount = debt.amount - amountToPay)
                debtDao.updateDebt(updatedDebt)
            }
        }
    }
}

class DebtViewModelFactory(private val debtDao: DebtDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DebtViewModel(debtDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
