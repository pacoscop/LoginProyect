package com.far.loginproyect.navigate

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.far.loginproyect.screens.home.HomeScreen
import com.far.loginproyect.screens.login.LoginScreen
import com.far.loginproyect.screens.login.LoginScreenViewModel
import com.far.loginproyect.screens.splash.SplashScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screens.SplashScreen.name) {
        composable(Screens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(Screens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(Screens.HomeScreen.name){
            HomeScreen(navController = navController)
        }
    }
}