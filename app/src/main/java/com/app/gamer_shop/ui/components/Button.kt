package com.app.gamer_shop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.gamer_shop.ui.theme.LightBlue

@Composable
fun Button(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Tab(
        selected = false,
        onClick = onClick,
        text = {
            Text(
                text = text,
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 20.sp),
                modifier = Modifier
                    .requiredWidth(width = 175.dp)
                    .requiredHeight(height = 33.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically))
        },
        modifier = modifier
            .requiredWidth(width = 196.dp)
            .requiredHeight(height = 49.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = LightBlue))
}

//@Preview(widthDp = 196, heightDp = 49)
//@Composable
//private fun ButtonPreview() {
//    Button(Modifier, "login")
//}