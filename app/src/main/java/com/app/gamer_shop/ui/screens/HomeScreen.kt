package com.app.gamer_shop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.gamer_shop.R
import com.app.gamer_shop.ui.components.Header
import com.app.gamer_shop.ui.components.NavBar
import com.app.gamer_shop.ui.components.ProductCard
import com.app.gamer_shop.ui.theme.Dark
import com.app.gamer_shop.ui.theme.LightGrey
import com.app.gamer_shop.viewModels.ProductViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel.products.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(searchQuery, selectedCategory) {
        productViewModel.loadProducts(category = selectedCategory, search = searchQuery)
    }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        containerColor = Dark,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = paddingValues.calculateTopPadding() + 20.dp,
                bottom = paddingValues.calculateBottomPadding() + 20.dp
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Header()
            }
            item {
                SearchField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it }
                )
            }
            item {
                CategoryNav(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { 
                        selectedCategory = if (selectedCategory == it) null else it 
                    }
                )
            }
            items(products.chunked(2)) { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    pair.forEach { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navController.navigate("product/${product.id}")
                            }
                        )
                    }
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LightGrey)
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = "Search our products...",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun CategoryNav(
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        CategoryItem(R.drawable.ic_mouse, "Mouse", selectedCategory == "Mouse") { onCategorySelected("Mouse") }
        CategoryItem(R.drawable.ic_keyboard, "Keyboard", selectedCategory == "Keyboard") { onCategorySelected("Keyboard") }
        CategoryItem(R.drawable.ic_headphone, "headphone", selectedCategory == "headphone") { onCategorySelected("headphone") }
        CategoryItem(R.drawable.ic_monitor, "Monitor", selectedCategory == "Monitor") { onCategorySelected("Monitor") }
    }
}

@Composable
fun CategoryItem(
    iconRes: Int,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.White else LightGrey),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = name,
                tint = if (isSelected) Color.Black else Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

//@Preview(widthDp = 379, heightDp = 821)
//@Composable
//private fun HomePagePreview() {
//    val navController = androidx.navigation.compose.rememberNavController()
//    HomeScreen(navController = navController, modifier = Modifier)
//}
