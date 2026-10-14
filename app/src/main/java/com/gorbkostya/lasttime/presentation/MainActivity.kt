package com.gorbkostya.lasttime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gorbkostya.lasttime.presentation.list.ListScreen
import com.gorbkostya.lasttime.presentation.ui.theme.LastTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LastTimeTheme {
                ListScreen()
            }
        }
    }
}
