package com.example.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.login.ui.theme.LoginTheme
// necessário para declarar o estatdo via delegation
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    var isLogged by remember {
        mutableStateOf(false)
    }
    var userState by remember {
        mutableStateOf("")
    }

    if (isLogged) {
        Welcome(user = userState)
    }
    else {
        Login(userState,
            onUserChange = {
                userState = it
            },
            onSuccess = {
                isLogged = true
        })
    }
}

@Composable
fun Welcome(user: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome, ${user}",
            style = MaterialTheme.typography.h1)
    }
}

@Composable
fun Login(user: String,
          onUserChange: (String) -> Unit,
          onSuccess: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
/*
        var userState = remember {
            mutableStateOf("")
        }
 */

        var passwordState by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current

        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")

        if (user.isNotBlank()) {
            Text(
                text = "Welcome, ${user}",
                style = MaterialTheme.typography.h4
            )
        }

        OutlinedTextField(
            value = user,
            onValueChange = {  onUserChange(it) }
        )
        OutlinedTextField(
            value = passwordState,
            onValueChange = { passwordState = it }
        )

        Button(onClick = {
            if (user.equals("admin") && passwordState.equals("admin")) {
                onSuccess()
                Toast.makeText(context, "Login ok", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Login inválido", Toast.LENGTH_LONG).show()
            }


        }) {
            Text(text = "Login")
        }

    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginTheme {
        Welcome(user = "admin")
    }
}