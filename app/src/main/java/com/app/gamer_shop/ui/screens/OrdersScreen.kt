package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.models.Order
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.OrderViewModel

@Composable
fun OrdersScreen(navController: NavController) {
    val viewModel: OrderViewModel = hiltViewModel()
    val orders by viewModel.orders.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchOrders()
    }

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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(orders) { order ->
                    OrderCard(order, onClick = {
                        navController.navigate("order/${order.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(LightGrey)
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Row {
                Text(
                    text = "Order id: ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = order.id.toString(),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Row {
                Text(
                    text = "Status: ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = order.status ?: "Unknown",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Row {
                Text(
                    text = "Overall price: ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${order.totalPrice}$",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}
