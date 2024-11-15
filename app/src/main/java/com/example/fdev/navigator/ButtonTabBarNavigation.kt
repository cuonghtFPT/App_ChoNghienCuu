package com.example.fdev.navigator

import RetrofitService
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fdev.R
import com.example.fdev.View.Admin.AddProductScreen
import com.example.fdev.View.Design.AddDesignScreen
import com.example.fdev.View.Design.HomeDesignScreen
import com.example.fdev.View.User.FavoritesScreen
import com.example.fdev.View.User.LayoutHomeScreen
import com.example.fdev.View.User.NotificationScreen
import com.example.fdev.View.User.ProfileScreen
import com.example.fdev.View.User.SearchScreen

import com.google.firebase.auth.FirebaseAuth


enum class ROUTER {
    home,
    favourite,
    Notification,
    search,
    person,
    ADDPRODUCT,
    CONGRATSADMIN,
    ADDPRODUCTDESIGNER,
    PRODUCTDESIGNER,
    HOMEDESIGN

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetLayoutButtonBarNavigator(navHostController: NavHostController) {
    var isSelected by rememberSaveable { mutableStateOf(ROUTER.home.name) }
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    val user = auth.currentUser
    val isAdmin = user?.displayName == "AdminFdev"
    val isDesigner = user?.displayName == "designerFdev"

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
            ) {
                // Home - luôn hiển thị
                NavigationBarItem(
                    selected = isSelected == (if (isDesigner) ROUTER.HOMEDESIGN.name else ROUTER.home.name),
                    onClick = {
                        isSelected = ROUTER.home.name
                        navController.navigate(ROUTER.home.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.home_anh),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )

                // Nếu là admin
                if (isAdmin) {
                    NavigationBarItem(
                        selected = isSelected == ROUTER.ADDPRODUCT.name,
                        onClick = {
                            isSelected = ROUTER.ADDPRODUCT.name
                            navController.navigate(ROUTER.ADDPRODUCT.name) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.add_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF059BEE),
                            unselectedIconColor = Color.Black,
                            indicatorColor = Color.White
                        )
                    )
                } else {
                    if (isDesigner) {
                        NavigationBarItem(
                            selected = isSelected == ROUTER.ADDPRODUCTDESIGNER.name,
                            onClick = {
                                isSelected = ROUTER.ADDPRODUCTDESIGNER.name
                                navController.navigate(ROUTER.ADDPRODUCTDESIGNER.name) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF059BEE),
                                unselectedIconColor = Color.Black,
                                indicatorColor = Color.White
                            )
                        )
                    } else {
                        // Nếu không phải admin (người dùng thông thường)
                        NavigationBarItem(
                            selected = isSelected == ROUTER.favourite.name,
                            onClick = {
                                isSelected = ROUTER.favourite.name
                                navController.navigate(ROUTER.favourite.name) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.favourite),
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF059BEE),
                                unselectedIconColor = Color.Black,
                                indicatorColor = Color.White
                            )
                        )
                    }
                }
//                nếu là designer

                NavigationBarItem(
                    selected = isSelected == ROUTER.Notification.name,
                    onClick = {
                        isSelected = ROUTER.Notification.name
                        navController.navigate(ROUTER.Notification.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = isSelected == ROUTER.search.name,
                    onClick = {
                        isSelected = ROUTER.search.name
                        navController.navigate(ROUTER.search.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_anh),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )
                NavigationBarItem(
                    selected = isSelected == ROUTER.person.name,
                    onClick = {
                        isSelected = ROUTER.person.name
                        navController.navigate(ROUTER.person.name) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059BEE),
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            // NavHost để quản lý các màn hình
            NavHost(
                navController = navController,
                startDestination = isSelected
            ) {
                composable(ROUTER.home.name) {
                    if (!isDesigner) {
                        LayoutHomeScreen(navHostController, RetrofitService())
                    } else {
                        HomeDesignScreen(navHostController)
                    }
                }
                composable(ROUTER.HOMEDESIGN.name) {
                    HomeDesignScreen(navHostController)
                }
                if (isAdmin) {
                    composable(ROUTER.ADDPRODUCT.name) {
                        AddProductScreen(navHostController)
                    }
                } else {
                    if (isDesigner) {
                        composable(ROUTER.ADDPRODUCTDESIGNER.name) {
                            AddDesignScreen(navHostController)
                        }
                    } else {
                        composable(ROUTER.favourite.name) {
                            FavoritesScreen(navHostController)
                        }
                    }
                }


                composable(ROUTER.Notification.name) {
                    NotificationScreen(navHostController)
                }
                composable(ROUTER.search.name) {
                    SearchScreen(navHostController, retrofitService = RetrofitService())
                }
                composable(ROUTER.person.name) {
                    ProfileScreen(navHostController)
                }

            }
        }
    }
}

//           adminfdev@gmail.com
//           admifdev000
//           designer@gmail.com
//           fdev000
