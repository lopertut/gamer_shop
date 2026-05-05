package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.ui.components.AuthTextField
import com.app.gamer_shop.ui.components.Button
import com.app.gamer_shop.ui.components.Label
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.viewModels.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loginResult.collect { success ->
            if (success) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Label("Login")
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter something..."
        )

        Spacer(modifier = Modifier.height(20.dp))

        Label("Password")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter something...",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            text = "Login",
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "or",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            text = "SignUp",
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
