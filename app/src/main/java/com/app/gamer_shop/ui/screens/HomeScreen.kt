package com.app.gamer_shop.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.app.gamer_shop.models.Product
import com.app.gamer_shop.views.ProductViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen() {
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel.products.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Inside

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .requiredWidth(width = 379.dp)
            .requiredHeight(height = 821.dp)
            .background(color = ColorsVariableCollection.color_dark)
            .padding(start = 25.dp,
                end = 25.dp,
                top = 30.dp)
    ) {
        item {
            Header()
        }
        item {
            SearchField()
        }
        item {
            CategoryNav()
        }
        item {
            FeaturedProducts()
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.component2),
                contentDescription = "Component 2",
                modifier = Modifier
                    .requiredWidth(width = 379.dp)
                    .requiredHeight(height = 54.dp)
                    .padding(horizontal = 50.dp,
                        vertical = 10.dp))
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(235.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        CF()
        Image(
            painter = painterResource(id = R.drawable.vector),
            contentDescription = "Vector",
            modifier = Modifier
                .requiredSize(size = 28.dp))
    }
}

@Composable
fun CF(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 50.dp)
            .requiredHeight(height = 28.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.White,
                    fontSize = 24.sp)) {append("C")}
                withStyle(style = SpanStyle(
                    color = Color(0xff00c8ff),
                    fontSize = 24.sp)) {append("&")}
                withStyle(style = SpanStyle(
                    color = Color.White,
                    fontSize = 24.sp)) {append("F")}},
            modifier = Modifier
                .fillMaxSize())
    }
}

@Composable
fun SearchField(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 39.dp)
    ) {
        TextField()
        Text(
            text = "Search our products...",
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(align = Alignment.CenterVertically))
        Image(
            painter = painterResource(id = R.drawable.materialsymbolssearchrounded),
            contentDescription = "material-symbols:search-rounded",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 7.dp)
                .fillMaxWidth()
                .requiredHeight(height = 25.dp)
                .padding(start = 7.357827186584473.dp,
                    end = 296.4153356552124.dp))
    }
}

@Composable
fun TextField(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = ColorsVariableCollection.color_light_grey))
    }
}

@Composable
fun CategoryNav(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .requiredSize(size = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .background(color = ColorsVariableCollection.color_light_grey))
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Vector",
                modifier = Modifier
                    .fillMaxSize())
        }
        Box(
            modifier = Modifier
                .requiredSize(size = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .background(color = ColorsVariableCollection.color_light_grey))
            Image(
                painter = painterResource(id = R.drawable.keyboard2),
                contentDescription = "keyboard 2",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 3.87.dp)
                    .fillMaxWidth()
                    .requiredHeight(height = 40.dp))
        }
        Box(
            modifier = Modifier
                .requiredSize(size = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .background(color = ColorsVariableCollection.color_light_grey))
            Image(
                painter = painterResource(id = R.drawable.customerserviceheadset2),
                contentDescription = "customer-service-headset 2",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 0.dp,
                        y = 8.57.dp)
                    .fillMaxWidth()
                    .requiredHeight(height = 32.dp))
        }
        Box(
            modifier = Modifier
                .requiredSize(size = 50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
                    .background(color = ColorsVariableCollection.color_light_grey))
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = "Group",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 10.dp,
                        y = 12.22.dp)
                    .requiredWidth(width = 30.dp)
                    .requiredHeight(height = 29.dp))
        }
    }
}

@Composable
fun FeaturedProducts(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 540.dp)
    ) {
        repeat(6) {
            ProductCard()
        }

    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 157.dp)
            .requiredHeight(height = 170.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = ColorsVariableCollection.color_light_grey))
        Image(
            painter = painterResource(id = R.drawable.product_image),
            contentDescription = "product_image",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp,
                    y = 6.dp)
                .fillMaxWidth()
                .requiredHeight(height = 103.dp)
                .clip(shape = RoundedCornerShape(15.dp)))
        Text(
            text = "Product name here Product name here",
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = Modifier
                .fillMaxSize())
        Text(
            text = "120 $",
            color = ColorsVariableCollection.color_light_blue,
            style = TextStyle(
                fontSize = 12.sp),
            modifier = Modifier
                .fillMaxSize())
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 109.dp,
                    y = 149.dp)
                .requiredWidth(width = 43.dp)
                .requiredHeight(height = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xffffdd00)))
            Text(
                text = "4.8",
                color = Color.White,
                style = TextStyle(
                    fontSize = 12.sp),
                modifier = Modifier
                    .fillMaxSize())
        }
    }
}

@Preview(widthDp = 379, heightDp = 821)
@Composable
private fun HomePagePreview() {
    HomePage(Modifier)
}
