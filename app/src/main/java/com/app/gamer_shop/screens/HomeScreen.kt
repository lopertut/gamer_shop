package com.app.gamer_shop.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.views.ProductViewModel

@Composable
fun HomeScreen(viewModel: ProductViewModel = viewModel()) {

    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff333131))
            .padding(16.dp)
    ) {

        Header()

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar()

        Spacer(modifier = Modifier.height(12.dp))

        Banner()

        Spacer(modifier = Modifier.height(16.dp))

        Categories()

        Spacer(modifier = Modifier.height(16.dp))

        FeaturedSection(products)

        Spacer(modifier = Modifier.weight(1f))

        BottomNav()
    }
}

@Composable
fun Header() {
    Text(
        text = buildAnnotatedString {
            append("C")
            withStyle(SpanStyle(color = Color(0xff06bac7))) { append("&") }
            append("F")
        },
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff696969), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "Search keyboards, mice...",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.DarkGray, RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "MECHANICAL MAYHEM\nUp to 30% OFF!",
            color = Color.White
        )
    }
}

@Composable
fun Categories() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        listOf("Mice", "Headsets", "Monitors", "Keyboards").forEach {
            Text(it, color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun FeaturedSection(products: List<Product>) {
    Column {
        Text(
            text = "FEATURED GEAR",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LazyColumn {
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .background(Color(0xff3e3e3e), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.name, color = Color.White, fontSize = 12.sp)

        Text(product.price, color = Color(0xff00c8ff))
    }
}

@Composable
fun BottomNav() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff525252))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("Home", "Orders", "Wishlist").forEach {
            Text(it, color = Color.White)
        }
    }
}

