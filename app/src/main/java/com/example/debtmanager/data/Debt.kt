package com.example.debtmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class Debt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val shopName: String,
    val amount: Double,
    val date: Long // Store as timestamp
)
