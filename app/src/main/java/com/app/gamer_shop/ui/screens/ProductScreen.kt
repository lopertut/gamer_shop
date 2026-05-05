package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
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
import com.app.gamer_shop.ui.components.Button
import com.app.gamer_shop.ui.components.Header
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightBlue
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.CartViewModel
import com.app.gamer_shop.viewModels.ProductViewModel

@Composable
fun ProductScreen(navController: NavController, productId: String?) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val product by productViewModel.product.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        productId?.toIntOrNull()?.let {
            productViewModel.loadProductById(it)
        }
    }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        containerColor = Dark
    ) { paddingValues ->
        product?.let { prod ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item {
                    Header(modifier = Modifier.padding(top = 20.dp))
                }

                // Product Image
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = prod.images?.firstOrNull(),
                            contentDescription = prod.name,
                            placeholder = painterResource(R.drawable.placeholder),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize().padding(20.dp)
                        )
                    }
                }

                // Dot indicators placeholder
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(4) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(if (index == 0) 12.dp else 8.dp, 8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (index == 0) Color.White else Color.Gray)
                            )
                        }
                    }
                }

                // Title
                item {
                    Text(
                        text = prod.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Price and Rating
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${prod.price}$",
                            color = LightBlue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RatingStars(rating = prod.rating)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${prod.rating} (328)",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Specs
                item {
                    SpecsGrid(prod.specs)
                }

                // Add to Cart Button
                item {
                    Button("Add to cart", onClick = { cartViewModel.addToCart(prod.id) })
                }

                // Rating Summary
                item {
                    RatingSummary()
                }

                // Review Input
                item {
                    ReviewInputBox()
                }

                // Reviews List
                items(dummyReviews) { review ->
                    ReviewItem(review)
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = LightBlue)
        }
    }
}

@Composable
fun RatingStars(rating: Double) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating.toInt()) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun SpecsGrid(specs: Map<String, Any>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val specList = specs.toList()
        for (i in specList.indices step 2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SpecItem(specList[i], Modifier.weight(1f))
                if (i + 1 < specList.size) {
                    SpecItem(specList[i + 1], Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SpecItem(spec: Pair<String, Any>, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "${spec.first}: ${spec.second}", color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun RatingSummary() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "4.8", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            RatingStars(rating = 4.8)
        }
        Column {
            RatingRow(5, 300)
            RatingRow(4, 25)
            RatingRow(3, 0)
            RatingRow(2, 0)
            RatingRow(1, 1)
        }
    }
}

@Composable
fun RatingRow(stars: Int, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$stars star", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.width(50.dp))
        Text(text = "($count)", color = Color.LightGray, fontSize = 12.sp)
    }
}

@Composable
fun ReviewInputBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(LightGrey)
            .padding(15.dp)
    ) {
        RatingStars(rating = 4.0)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "write yor opinion here ".repeat(10),
            color = Color.White,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Upload", color = Color.Black, fontSize = 12.sp)
        }
    }
}

data class Review(val user: String, val date: String, val rating: Double, val text: String)

val dummyReviews = listOf(
    Review("Usermae", "dd.mm.yyyy", 4.0, "Text ".repeat(20)),
    Review("Usermae", "dd.mm.yyyy", 4.0, "Text ".repeat(20)),
    Review("Aleksei Martinov", "12.06.2027", 5.0, "Text ".repeat(20))
)

@Composable
fun ReviewItem(review: Review) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        HorizontalDivider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(bottom = 10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingStars(rating = review.rating)
            Text(text = review.user, color = Color.Gray, fontSize = 12.sp)
            Text(text = review.date, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = review.text, color = Color.White, fontSize = 12.sp)
    }
}
