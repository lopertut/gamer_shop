package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.ui.components.AuthTextField
import com.app.gamer_shop.ui.components.Button
import com.app.gamer_shop.ui.components.Label
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark

@Composable
fun DeliveryScreen(navController: NavController) {
    var country by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        containerColor = Dark,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 25.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Label("Country")
            AuthTextField(
                value = country,
                onValueChange = { country = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("Address")
            AuthTextField(
                value = address,
                onValueChange = { address = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("Postal code")
            AuthTextField(
                value = postalCode,
                onValueChange = { postalCode = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("Firstname")
            AuthTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("LastName")
            AuthTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("Email")
            AuthTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                text = "Procced",
                onClick = {
                    navController.navigate("payment")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
