package com.far.loginproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.far.loginproyect.screens.login.LoginScreenViewModel
import com.far.loginproyect.screens.login.MyLoginView
import com.far.loginproyect.ui.theme.LoginProyectTheme
import com.far.loginproyect.navigate.Navigation



class MainActivity : ComponentActivity() {

    private val loginScreenViewModel: LoginScreenViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginProyectTheme {

                Surface(
                    modifier = Modifier.fillMaxSize().padding(top = 46.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Navigation()
                        //MyLoginView(LoginScreenViewModel())
                    }
                }
            }
        }
    }
}


