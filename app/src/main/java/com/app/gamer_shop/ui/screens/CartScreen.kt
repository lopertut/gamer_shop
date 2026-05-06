package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.models.CartItem
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.ui.components.Header
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.components.ProductCard
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightBlue
import com.app.gamer_shop.viewModels.CartViewModel

@Composable
fun CartScreen(navController: NavController) {
    val viewModel: CartViewModel = hiltViewModel()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCart()
    }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        containerColor = Dark,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 25.dp)
        ) {
            Header()

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                items(cartItems) { item ->
                    CartItemRow(item, viewModel)
                }
            }

            CartSummary(cartItems)

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("delivery") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "Checkout",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, viewModel: CartViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductCard(
            product = Product(
                id = item.productId,
                name = item.name,
                price = item.price,
                images = item.images,
                rating = item.rating,
                specs = emptyMap(),
                category = "",
                brand = ""
            ),
            modifier = Modifier.width(160.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Quantity Controls Part
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            IconButton(
                onClick = { viewModel.increaseCartItem(item) },
                modifier = Modifier
                    .size(30.dp)
                    .border(1.dp, LightBlue, RoundedCornerShape(4.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Increase",
                    tint = LightBlue,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = item.quantity.toString(),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { viewModel.decreaseCartItem(item) },
                modifier = Modifier
                    .size(30.dp)
                    .border(1.dp, LightBlue, RoundedCornerShape(4.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Decrease",
                    tint = LightBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun CartSummary(items: List<CartItem>) {
    val subtotal = items.sumOf { it.price * it.quantity }
    val vat = 20.0
    val shipping = 10.0
    val total = subtotal + vat + shipping

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SummaryRow("VAT:", "${vat.toInt()}$")
        SummaryRow("shipping fee:", "${shipping.toInt()}$")
        
        Row(
            modifier = Modifier.width(230.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Total:", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = "${total.toInt()}$", color = LightBlue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.width(230.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White, fontSize = 20.sp)
        Text(text = value, color = LightBlue, fontSize = 20.sp)
    }
}
