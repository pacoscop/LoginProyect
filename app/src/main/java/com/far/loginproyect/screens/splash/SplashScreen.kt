package com.far.loginproyect.screens.splash

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.far.loginproyect.navigate.Navigation
import com.far.loginproyect.navigate.Screens
import com.far.loginproyect.screens.login.LoginScreenViewModel
import com.far.loginproyect.screens.login.MyLoginView
import com.far.loginproyect.ui.theme.LoginProyectTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch





@Composable
fun  SplashScreen(navController: NavController){


    //ANIMACIÓN
    val scale = remember {Animatable(0f)}
    val rotation = remember { Animatable(0f) }
    val shakeOffset = remember {Animatable(0f)}
    val color = MaterialTheme.colorScheme.primary
    var click by rememberSaveable { mutableStateOf(0) }
    // Efecto de shake al hacer clic
  // Distancia de movimiento
    val shakeCount = 10 // Número de sacudidas
    var alpha = remember {Animatable(0f)}
    var spacer = remember {Animatable (0f)}


    //Animación Shake
    LaunchedEffect(click) {
            var shakeDistance = click // Usar el valor actual de click como distancia de movimiento
            for (i in 0 until shakeCount) {
                shakeOffset.animateTo(targetValue = shakeDistance.toFloat(), animationSpec = tween(durationMillis = 50))
                shakeOffset.animateTo(targetValue = -shakeDistance.toFloat(), animationSpec = tween(durationMillis = 50))
            }
            shakeOffset.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 50))

    }

    //Animación tras animación entrada
    LaunchedEffect(true){
        delay(3000)
        launch{

            alpha.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(durationMillis = 1000))
        }
        launch {
            spacer.animateTo(
                targetValue = 15f,
                animationSpec = tween(durationMillis = 1000)
            )

        }


    }

    LaunchedEffect(key1 = true) {
        launch {  scale.animateTo(
            targetValue = 0.6f,
            //Comportamiento de la animación - efecto- rebote
            animationSpec = tween(durationMillis = 2000,
                easing = { OvershootInterpolator(20f).getInterpolation(it)
                } ))
        }
        launch {
            rotation.animateTo(
                targetValue = 180f,
                animationSpec = tween(durationMillis = 2000,
                    easing = {OvershootInterpolator(2f).getInterpolation(it)})
            )
            delay(300)
            launch {
                launch {  scale.animateTo(
                    targetValue = 1f,
                    //Comportamiento de la animación - efecto- rebote
                    animationSpec = tween(durationMillis = 500,
                        easing = { OvershootInterpolator(2f).getInterpolation(it)
                        } )) }
                launch {
                    rotation.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(durationMillis = 500))
                }

            }

        }
        delay (4000)

        //Ir a la siguiente pantalla
        navController.navigate(Screens.LoginScreen.name)
        // si ya está logueado el usuario no necesita autenticarse de nuevo
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(Screens.LoginScreen.name)
        }else{
            navController.navigate(Screens.LoginScreen.name){
                //al pulsar boton atras vuelve a splash, para evitar esto
                //sacamos el splash de la lista de pantallas recorridas
                popUpTo(Screens.SplashScreen.name){
                    inclusive = true
                }

            }

        }


    }


    Box (modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        ){
        Surface (modifier = Modifier
            .padding(15.dp)//
            .size(250.dp)

            .scale(scale.value)
            .graphicsLayer(rotationZ = rotation.value, translationX = shakeOffset.value)
            .clickable(onClick = {
                click += 5}),
            shape = CircleShape,
            border = BorderStroke(width = 2.dp, color = color),
        )
        {
            Column (modifier = Modifier
                .padding(1.dp),
                verticalArrangement = Arrangement.Center,
                Alignment.CenterHorizontally)
            {
                Text(
                    text = "Spotify",
                    style = MaterialTheme.typography.titleLarge,
                    color = color.copy(alpha = 0.5f)
                )
               Spacer(modifier = Modifier.height(spacer.value.dp))
                Text(
                    text = "Login Proyect",
                    style = MaterialTheme.typography.titleMedium,
                    color = color.copy(alpha = alpha.value),

                )
            }
        }
    }
    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        Button(onClick = {click = 0}) {
            Text("Nivel de temblor: $click")
        }
    }


}


@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSplashScreen(){
    val navController = rememberNavController()
    LoginProyectTheme {
        SplashScreen(navController = navController)
    }
}


