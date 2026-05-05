package com.app.gamer_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.gamer_shop.ui.screens.CartScreen
import com.app.gamer_shop.ui.screens.DeliveryScreen
import com.app.gamer_shop.ui.screens.PaymentScreen
import com.app.gamer_shop.ui.screens.HomeScreen
import com.app.gamer_shop.ui.screens.LoginScreen
import com.app.gamer_shop.ui.screens.OrderScreen
import com.app.gamer_shop.ui.screens.OrdersScreen
import com.app.gamer_shop.ui.screens.ProductScreen
import com.app.gamer_shop.ui.screens.ProfileScreen
import com.app.gamer_shop.ui.screens.SignUpScreen
import com.app.gamer_shop.ui.theme.Gamer_shopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Gamer_shopTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("login") {
                        LoginScreen(navController = navController)
                    }
                    composable("signup") {
                        SignUpScreen(navController = navController)
                    }
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                    composable("product/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        ProductScreen(navController = navController, productId = productId)
                    }
                    composable("cart") {
                        CartScreen(navController = navController)
                    }
                    composable("delivery") {
                        DeliveryScreen(navController = navController)
                    }
                    composable("payment") {
                        PaymentScreen(navController = navController)
                    }
                    composable("profile") {
                        ProfileScreen(navController = navController)
                    }
                    composable("orders") {
                        OrdersScreen(navController = navController)
                    }
                    composable("order/{orderId}") { backStackEntry ->
                        val orderId = backStackEntry.arguments?.getString("orderId")
                        OrderScreen(navController = navController, orderId = orderId)
                    }
                }
            }
        }
    }
}
