package com.far.loginproyect.screens.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.far.loginproyect.model.User

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch



class LoginScreenViewModel2: ViewModel() {
    //OJO! DAR PERMISO INTERNET EN MANIFEST
    //la estaremos usando a lo largo del proyecto
    private val auth: FirebaseAuth = Firebase.auth;

    //impide que se creen varios usuarios accidentalmente
    private val _loading = MutableLiveData(false)

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task -> //si la tarea tuvo exito escribimos mensaje en log
                        if (task.isSuccessful){
                            Log.d("MyLogin", "Google logueado!")
                            home()
                        } else{
                            Log.d(
                                "MyLogin",
                                "signInWithGoogle: ${task.result.toString()}"
                            )
                        }
                    }
            }catch (ex: Exception){
                Log.d("MYLogin", "Error al loguear con Google: ${ex.message}")
            }
        }

    fun signInWithFacebook(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task -> //si la tarea tuve exito escribimos mensaje en log
                        if (task.isSuccessful) {
                            Log.d("MyLogin", "Facebook logueado!!!!")
                            home()
                        } else {
                            Log.d(
                                "MyLogin",
                                "signInWithFacebook: ${task.result.toString()}"
                            )
                        }
                    }
            } catch (ex: Exception) {
                Log.d("MyLogin", "Error al loguear con Facebook: ${ex.message}")
            }
        }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch{ //para que se ejecute en segundo plano
            try{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener  {task -> //si la tarea tuvo exito escribimos mensaje en el log
                        if (task.isSuccessful){
                            Log.d("MyLogin","signInWithEmailAndPassword logueado!!!")
                            home()
                        } else{
                            Log.d(
                                "MyLogin",
                                "signInWithEmailAndPassword: ${task.result.toString()}"
                            )
                        }
                    }
            }catch (ex: Exception){
                Log.d("MyLogin", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ){
        if(_loading.value == false){ //para que no vuelva a entrar accidentamente y cree muchos usuarios
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val displayName =
                            task.result.user?.email?.split("@")
                                ?.get(0) //Extrae el username para el firestor
                        createUser(displayName) //para el firestore
                        home()
                    } else{
                        Log.d(
                            "MyLogin",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?){
        val userId = auth.currentUser?.uid //sacamos la Id del auth
        //val user = mutableMapOf<String, Any>() //nos permite crear parejas de objetos clave, valor

        //user["user_id"] = userId.toString() //pareja clave, valor
        //user["display_name"] = displayName.toString()

        //usando un data class
        val user = User(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "lo dificil ya pasó",
            profession = "Android Dev",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance()
            .collection("users") //con esto referenciamos la coleccion que creamos cloud Firestore
            .add(user)
            .addOnSuccessListener {
                Log.d("MyLogin", "Creado ${it.id}")
            }
            .addOnFailureListener{
                Log.d("MyLogin", "Ocurrió Error: ${it}")
            }
    }
    }


