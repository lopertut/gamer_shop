package com.app.gamer_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.gamer_shop.ui.screens.*
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
                    composable(
                        route = "payment/{country}/{address}/{postalCode}/{firstName}/{lastName}/{email}",
                        arguments = listOf(
                            navArgument("country") { type = NavType.StringType },
                            navArgument("address") { type = NavType.StringType },
                            navArgument("postalCode") { type = NavType.StringType },
                            navArgument("firstName") { type = NavType.StringType },
                            navArgument("lastName") { type = NavType.StringType },
                            navArgument("email") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        PaymentScreen(
                            navController = navController,
                            country = backStackEntry.arguments?.getString("country") ?: "",
                            address = backStackEntry.arguments?.getString("address") ?: "",
                            postalCode = backStackEntry.arguments?.getString("postalCode") ?: "",
                            firstName = backStackEntry.arguments?.getString("firstName") ?: "",
                            lastName = backStackEntry.arguments?.getString("lastName") ?: "",
                            email = backStackEntry.arguments?.getString("email") ?: ""
                        )
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
