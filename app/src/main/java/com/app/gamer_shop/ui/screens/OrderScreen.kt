package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.models.Order
import com.app.gamer_shop.models.OrderItem
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.OrderViewModel

@Composable
fun OrderScreen(navController: NavController, orderId: String?) {
    val viewModel: OrderViewModel = hiltViewModel()
    val orderItems by viewModel.orderItems.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()

    val currentOrder = orders.find { it.id == orderId?.toIntOrNull() }

    LaunchedEffect(orderId) {
        orderId?.toIntOrNull()?.let {
            viewModel.fetchOrderItems(it)
            viewModel.fetchOrders()
        }
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(orderItems) { item ->
                    OrderItemCard(item)
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    OrderSummary(currentOrder)
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(item: OrderItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(LightGrey)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.productName ?: "Product Name",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${item.price}$", color = Color.White, fontSize = 14.sp)
                Text(text = "x${item.quantity}", color = Color.LightGray, fontSize = 14.sp)
            }
            Text(
                text = "Total: ${item.price * item.quantity}$",
                color = com.app.gamer_shop.ui.theme.LightBlue,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}

@Composable
fun OrderSummary(order: Order?) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryInfoRow("delivery address:", order?.let { "${it.country}, ${it.address}" } ?: "")
        SummaryInfoRow("Name:", order?.let { "${it.firstname} ${it.lastname}" } ?: "")
        SummaryInfoRow("Email:", order?.email ?: "")
        SummaryInfoRow("time order:", order?.createdAt ?: "")
    }
}

@Composable
fun SummaryInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White, fontSize = 14.sp, modifier = Modifier.weight(1f))
        Text(text = value, color = Color.White, fontSize = 14.sp, modifier = Modifier.weight(1.5f))
    }
}
