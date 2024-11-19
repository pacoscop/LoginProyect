package com.far.loginproyect.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.far.loginproyect.R

import com.far.loginproyect.ui.theme.LoginProyectTheme
import com.far.loginproyect.ui.theme.SpotifyColor



class LoginScreenViewModel: ViewModel(){

    //Clase SALVA
    @Composable
    fun LoginScreen(navController: NavController, ){}
    //Clase SALVA


}

    @Composable
    fun MyLoginView(viewModel: LoginScreenViewModel){
        var textUsuario by rememberSaveable { mutableStateOf("") }
        var textContrasena by rememberSaveable { mutableStateOf("") }
        var botonAcceder by rememberSaveable { mutableStateOf(false) }
        var crearCuenta by rememberSaveable { mutableStateOf(false) }
        Column (modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


         ){
            Image(painter = painterResource(R.drawable.spotify_logo),
                   contentDescription = "LogoSpotify",
                modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.20f).padding(bottom = 10.dp))

            Text("Spotify", modifier = Modifier.padding(vertical = 2.dp),
                style = TextStyle(fontSize = 35.sp),
                color = MaterialTheme.colorScheme.onBackground )
            TextField(
                value = textUsuario,
                onValueChange = { newText -> textUsuario = newText},
                modifier = Modifier.padding(top = 10.dp),
                label = {if(!crearCuenta) {Text("Usuario", modifier = Modifier)} else {
                    Text("Nuevo Usuario", modifier = Modifier)}
                }
            )
            TextField(
                value = textContrasena,
                onValueChange = { newText -> textContrasena = newText},
                modifier = Modifier.padding(bottom = 10.dp),
                label = {if(!crearCuenta) {Text("Contraseña", modifier = Modifier)} else {
                    Text("Nueva contraseña", modifier = Modifier)}}
            )
            Button(onClick = {botonAcceder = !botonAcceder},
                modifier = Modifier.padding(bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(SpotifyColor),           ) {
                Text(if(!crearCuenta) {"Acceder"}else{"Crear cuenta"})
            }
            Text("No tengo cuenta, click aquí",
                Modifier.clickable { crearCuenta = !crearCuenta },
                color = MaterialTheme.colorScheme.onBackground)
            Row (modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically){
                Text("Acceder con ", color = MaterialTheme.colorScheme.onBackground)
                Image(painter = painterResource(R.drawable.el_logo_g_de_google),
                    contentDescription = "Logo Google",
                    modifier = Modifier.fillMaxWidth(0.1f)
                    .fillMaxHeight(0.07f))
            }
        }

    }


    @Preview(showSystemUi = true, showBackground = true)
    @Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewLoginScreen(){
        LoginProyectTheme {
            MyLoginView(viewModel = LoginScreenViewModel())
        }
    }
