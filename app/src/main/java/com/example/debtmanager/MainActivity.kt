package com.example.debtmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.debtmanager.data.AppDatabase
import com.example.debtmanager.ui.DebtAppUI
import com.example.debtmanager.ui.DebtViewModel
import com.example.debtmanager.ui.DebtViewModelFactory
import com.example.debtmanager.ui.theme.DebtManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val debtDao = database.debtDao()
        
        val viewModel: DebtViewModel by viewModels {
            DebtViewModelFactory(debtDao)
        }

        setContent {
            DebtManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DebtAppUI(viewModel = viewModel)
                }
            }
        }
    }
}
