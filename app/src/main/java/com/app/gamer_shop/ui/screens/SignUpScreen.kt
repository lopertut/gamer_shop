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
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.registerResult.collect { success ->
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

        Spacer(modifier = Modifier.height(30.dp))

        Label("Username")
        AuthTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = "Enter something..."
        )

        Spacer(modifier = Modifier.height(15.dp))

        Label("Email")
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter something..."
        )

        Spacer(modifier = Modifier.height(15.dp))

        Label("Password")
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter something...",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            text = "SignUp",
            onClick = {
                viewModel.register(username, email, password)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "or",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            text = "Login",
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
