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
import androidx.compose.runtime.*
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
import com.app.gamer_shop.models.Review
import com.app.gamer_shop.ui.components.Button
import com.app.gamer_shop.ui.components.Header
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightBlue
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.CartViewModel
import com.app.gamer_shop.viewModels.ProductViewModel
import com.app.gamer_shop.viewModels.ReviewViewModel

@Composable
fun ProductScreen(navController: NavController, productId: String?) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val reviewViewModel: ReviewViewModel = hiltViewModel()
    
    val product by productViewModel.product.collectAsStateWithLifecycle()
    val reviews by reviewViewModel.reviews.collectAsStateWithLifecycle()

    var reviewText by remember { mutableStateOf("") }
    var reviewRating by remember { mutableIntStateOf(5) }

    LaunchedEffect(productId) {
        productId?.toIntOrNull()?.let {
            productViewModel.loadProductById(it)
            reviewViewModel.loadReviews(it)
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

                // Prices and Rating
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
                            RatingStars(rating = prod.rating.toInt())
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${prod.rating} (${reviews.size})",
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
                    RatingSummary(reviews)
                }

                // Review Input
                item {
                    ReviewInputBox(
                        rating = reviewRating,
                        onRatingChange = { reviewRating = it },
                        text = reviewText,
                        onTextChange = { reviewText = it },
                        onUpload = {
                            reviewViewModel.addReview(prod.id, reviewRating, reviewText)
                            reviewText = ""
                        }
                    )
                }

                // Reviews List
                items(reviews) { review ->
                    ReviewItem(review)
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = LightBlue)
        }
    }
}

@Composable
fun RatingStars(rating: Int, onRatingChange: ((Int) -> Unit)? = null) {
    Row {
        repeat(5) { index ->
            val starIndex = index + 1
            IconButton(
                onClick = { onRatingChange?.invoke(starIndex) },
                enabled = onRatingChange != null,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (starIndex <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(18.dp)
                )
            }
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
fun RatingSummary(reviews: List<Review>) {
    val averageRating = if (reviews.isEmpty()) 0.0 else reviews.map { it.rating }.average()
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format("%.1f", averageRating),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            RatingStars(rating = averageRating.toInt())
        }
        Column {
            for (i in 5 downTo 1) {
                RatingRow(i, reviews.count { it.rating == i })
            }
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
fun ReviewInputBox(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    onUpload: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(LightGrey)
            .padding(15.dp)
    ) {
        RatingStars(rating = rating, onRatingChange = onRatingChange)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Write your opinion here", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = onUpload,
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
            Text(text = review.username ?: "Anonymous", color = Color.Gray, fontSize = 12.sp)
            // Backend might not return a date in the format you expect, or we might need to parse it.
            // For now, let's keep it simple.
            Text(text = "", color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = review.comment, color = Color.White, fontSize = 12.sp)
    }
}
