package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightGrey

@Composable
fun OrderScreen(navController: NavController, orderId: String?) {
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
                items(3) { // Mocking 3 items as in the image
                    OrderDetailItem()
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    OrderSummary()
                }
            }
        }
    }
}

@Composable
fun OrderDetailItem() {
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
            Text(text = "Product Name", color = Color.White, fontSize = 16.sp)
            Text(text = "price", color = Color.White, fontSize = 16.sp)
            Text(text = "quantity", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun OrderSummary() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryInfoRow("delivery address:", "estonia, tallinn, tallinn mnt.")
        SummaryInfoRow("Name:", "Abdul Ahmath")
        SummaryInfoRow("Email:", "test@email.com")
        SummaryInfoRow("time order:", "23.12.2023")
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
