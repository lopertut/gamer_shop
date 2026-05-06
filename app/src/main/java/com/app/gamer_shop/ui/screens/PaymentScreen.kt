package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.ui.components.AuthTextField
import com.app.gamer_shop.ui.components.Button
import com.app.gamer_shop.ui.components.Label
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.viewModels.CartViewModel
import com.app.gamer_shop.viewModels.OrderViewModel

@Composable
fun PaymentScreen(
    navController: NavController,
    country: String,
    address: String,
    postalCode: String,
    firstName: String,
    lastName: String,
    email: String
) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    
    val orderResult by orderViewModel.orderResult.collectAsStateWithLifecycle(initialValue = null)

    var cardNumber by remember { mutableStateOf("") }
    var cardOwner by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        cartViewModel.loadCart()
    }

    LaunchedEffect(orderResult) {
        if (orderResult == true) {
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    val totalPrice = cartItems.sumOf { it.price * it.quantity } + 30.0 // VAT + Shipping

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
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Label("Card number")
            AuthTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Label("Card owner")
            AuthTextField(
                value = cardOwner,
                onValueChange = { cardOwner = it },
                placeholder = "Enter something..."
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Label("Expiration date")
                    AuthTextField(
                        value = expirationDate,
                        onValueChange = { expirationDate = it },
                        placeholder = "Enter something..."
                    )
                }
                Column(modifier = Modifier.weight(0.7f)) {
                    Label("CVV")
                    AuthTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        placeholder = "Enter something..."
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                text = "Pay",
                onClick = {
                    orderViewModel.createOrder(
                        country = country,
                        address = address,
                        postalCode = postalCode,
                        firstname = firstName,
                        lastname = lastName,
                        email = email,
                        totalPrice = totalPrice.toFloat()
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
