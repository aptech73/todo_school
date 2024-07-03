package dev.aptech.todoapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aptech.todoapp.ui.apptheme.AppTheme
import dev.aptech.todoapp.ui.navigator.InitNavigator


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navHostController = rememberNavController()
                navHostController.InitNavigator()
            }
        }

    }
}