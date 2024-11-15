package com.example.fdev.View


import CartViewModel
import RetrofitService
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fdev.View.Admin.ProductAdmin
import androidx.navigation.navArgument
import com.example.fdev.View.Admin.UpdateProductScreenAdmin
import com.example.fdev.View.Design.AddDesignScreen
import com.example.fdev.View.Design.HomeDesignScreen
import com.example.fdev.View.Design.ProductDesigner
import com.example.fdev.View.Design.UpdateProductScreenDesigner
import com.example.fdev.View.Design.ViewProductDetailsDesigner
import com.example.fdev.View.User.AddPaymentMethod
import com.example.fdev.View.User.CartScreen
import com.example.fdev.View.User.CheckoutScreen
import com.example.fdev.View.User.CongratsScreen
import com.example.fdev.View.User.FavoritesScreen
import com.example.fdev.View.User.LayoutAccounts
import com.example.fdev.View.User.LayoutBillScreen
import com.example.fdev.View.User.LayoutContact
import com.example.fdev.View.User.LayoutHelp
import com.example.fdev.View.User.LayoutHistoryBuyProductScreen

import com.example.fdev.View.User.LayoutLoginScreen
import com.example.fdev.View.User.LayoutMail
import com.example.fdev.View.User.LayoutProductScreen
import com.example.fdev.View.User.LayoutRegisterScreen
import com.example.fdev.View.User.LayoutSetting
import com.example.fdev.View.User.LayoutWelcome
import com.example.fdev.View.User.NotificationScreen
import com.example.fdev.View.User.PaymentMethodScreen
import com.example.fdev.View.User.PrivacyPolicyScreen
import com.example.fdev.View.User.ProfileScreen
import com.example.fdev.View.User.ReviewScreen
import com.example.fdev.View.User.SearchScreen
import com.example.fdev.navigator.GetLayoutButtonBarNavigator
import com.example.fdev.navigator.ROUTER
import com.example.fdev.ViewModel.ProductAdminViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val retrofitService = RetrofitService() // Initialize RetrofitService
        val cartViewModel = CartViewModel()  // Initialize CartViewModel
        val productAdminViewModel: ProductAdminViewModel = viewModel() // Initialize ProductAdminViewModel

        NavHost(navController = navController, startDestination = Router.WELCOME.name) {
            composable(Router.WELCOME.name) {
                LayoutWelcome(navController = navController)
            }
            composable(Router.SETTING.name) {
                LayoutSetting(navController = navController)
            }
            composable(Router.HOME.name) {
                GetLayoutButtonBarNavigator(navController)
            }
            composable(Router.LOGIN.name) {
                LayoutLoginScreen(navController = navController)
            }
            composable(Router.REGISTER.name) {
                LayoutRegisterScreen(navController = navController)
            }
            composable(Router.PRODUCT.name) {
                LayoutProductScreen(navController = navController, cartViewModel = cartViewModel)
            }
            composable(Router.HELP.name) {
                LayoutHelp(navController = navController)
            }
            composable(Router.CONTACT.name) {
                LayoutContact(navController = navController)
            }
            composable(Router.MAIL.name) {
                LayoutMail(navController = navController)
            }
            composable(Router.CART.name) {
                CartScreen(navController = navController)
            }
            composable(
                route = "${Router.CHECKOUT.name}/{totalPrice}",
                arguments = listOf(navArgument("totalPrice") { type = NavType.StringType })
            ) { backStackEntry ->
                val totalPrice = backStackEntry.arguments?.getString("totalPrice") ?: "0.0"
                CheckoutScreen(navController = navController,retrofitService=retrofitService ,totalPrice = totalPrice)
            }
            composable(Router.FAVORITES.name) {
                FavoritesScreen(navController = navController)
            }
            composable(Router.SEARCH.name) {
                SearchScreen(navController = navController,retrofitService=retrofitService)
            }
            composable(Router.NOTIFICATIONS.name) {
                NotificationScreen(navController = navController)
            }
            composable(Router.CONGRATSSCREEN.name) {
                CongratsScreen(navController = navController)
            }
            composable(Router.PAYMENTMETHODSCREEN.name) {
                PaymentMethodScreen(navController = navController)
            }
            composable(Router.ADDPAYMENTMETHOD.name) {
                AddPaymentMethod(navController = navController,retrofitService=retrofitService)
            }
            composable(Router.REVIEW.name + "/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ReviewScreen(navController = navController,productId=productId, productName = String())
            }
            composable(Router.HISTORYBUYPRODUCT.name){
                LayoutHistoryBuyProductScreen(navController = navController)
            }

            composable(Router.ACCOUNTS.name) {
                LayoutAccounts(navController = navController)
            }
            composable(Router.BILL.name) {
                LayoutBillScreen(navController=navController)
            }
            composable(Router.PROFILE.name) {
                ProfileScreen(navController=navController)
            }
            composable(Router.PRIVACYPOICY.name) {
                PrivacyPolicyScreen(navController=navController)
            }
            composable(Router.VIEWProductDetailsDesigner.name) {
                ViewProductDetailsDesigner(navController = navController, cartViewModel = cartViewModel)
            }

            composable("updateProduct/{productId}/{productName}/{productPrice}/{productDescription}/{productImage}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                val productName = backStackEntry.arguments?.getString("productName") ?: ""
                val productPrice = backStackEntry.arguments?.getString("productPrice") ?: ""
                val productDescription =
                    backStackEntry.arguments?.getString("productDescription") ?: ""
                val productImage = backStackEntry.arguments?.getString("productImage") ?: ""

                UpdateProductScreenAdmin(
                    productId,
                    productName,
                    productPrice,
                    productDescription,
                    productImage,
                    navController = navController
                )
            }

            composable("updateProductDesigner/{designId}/{designName}/{designPrice}/{designDescription}/{designImageUri}") { backStackEntry ->
                val designId = backStackEntry.arguments?.getString("designId")
                val designName = backStackEntry.arguments?.getString("designName")
                val designPrice = backStackEntry.arguments?.getString("designPrice")
                val designDescription = backStackEntry.arguments?.getString("designDescription")
                val designImageUri = backStackEntry.arguments?.getString("designImageUri")

                // Kiểm tra nếu tất cả các tham số cần thiết đều có giá trị
                if (designId != null && designName != null && designPrice != null && designDescription != null && designImageUri != null) {
                    UpdateProductScreenDesigner(
                        navController = navController,
                        designId = designId,  // Truyền designId vào màn hình
                        designName = designName,
                        designPrice = designPrice,
                        designDescription = designDescription,
                        designImageUri = designImageUri
                    )
                }
            }

            composable(ROUTER.CONGRATSADMIN.name) {
                CongratsAdminScreen(navController)
            }
            composable(Router.ProductAdmin1.name) {
                ProductAdmin(navController)
            }

            composable(Router.ADDDESIGN.name) {
                AddDesignScreen(navController = navController)
            }
            composable(Router.HOMEDESIGN.name) {
                HomeDesignScreen(navController = navController)
            }

            composable(ROUTER.PRODUCTDESIGNER.name) {
                ProductDesigner(navController)
            }
        }
    }

    enum class Router {
        WELCOME,
        SETTING,
        HOME,
        LOGIN,
        REGISTER,
        PRODUCT,
        HELP,
        CONTACT,
        MAIL,
        HISTORYBUYPRODUCT,
        CART,
        CHECKOUT,
        FAVORITES,
        SEARCH,
        NOTIFICATIONS,
        CONGRATSSCREEN,
        PAYMENTMETHODSCREEN,
        ADDPAYMENTMETHOD,
        REVIEW,
        ACCOUNTS,
        ProductAdmin1,
        BILL,
        PROFILE,
        PRODUCTDESIGNER,
        ADDDESIGN,
        HOMEDESIGN,
        PRIVACYPOICY,
        VIEWProductDetailsDesigner,
    }
}