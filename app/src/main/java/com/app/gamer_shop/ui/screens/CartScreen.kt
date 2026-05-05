package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import androidx.navigation.NavController
import com.app.gamer_shop.R
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.ui.components.Header
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightBlue
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.CartUiItem
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
        containerColor = Dark
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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(cartItems) { item ->
                    CartItemRow(item, viewModel)
                }
            }

            CartSummary(cartItems)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(55.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = "Checkout",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CartItemRow(item: CartUiItem, viewModel: CartViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Card Part
        Box(
            modifier = Modifier
                .size(157.dp, 170.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF3E3E3E))
                .padding(8.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(103.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF525252)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = item.product.images?.firstOrNull(),
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.product.name, color = Color.White, fontSize = 12.sp, maxLines = 2)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${item.product.price}$", color = LightBlue, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = Color.Yellow, modifier = Modifier.size(12.dp))
                        Text(text = "${item.product.rating}", color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(30.dp))

        // Quantity Controls Part
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            IconButton(
                onClick = { viewModel.incrementQuantity(item) },
                modifier = Modifier
                    .size(30.dp)
                    .border(1.dp, LightBlue, RoundedCornerShape(4.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = LightBlue,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = "${item.cartItem.quantity}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { viewModel.decrementQuantity(item) },
                modifier = Modifier
                    .size(30.dp)
                    .border(1.dp, LightBlue, RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp, 2.dp)
                        .background(LightBlue)
                )
            }
        }
    }
}

@Composable
fun CartSummary(items: List<CartUiItem>) {
    val subtotal = items.sumOf { it.product.price * it.cartItem.quantity }
    val vat = 20.0
    val shipping = 10.0
    val total = subtotal + vat + shipping

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SummaryRow("VAT:", "${vat.toInt()}$")
        SummaryRow("shipping fee:", "${shipping.toInt()}$")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total:", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "${total.toInt()}$", color = LightBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White, fontSize = 18.sp)
        Text(text = value, color = LightBlue, fontSize = 18.sp)
    }
}
