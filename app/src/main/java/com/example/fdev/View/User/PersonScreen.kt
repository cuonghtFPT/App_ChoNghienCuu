package com.example.fdev.View.User

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PersonScreen() {
    LayoutPersonScreen(navController = rememberNavController())
}

@Composable
fun LayoutPersonScreen(navController: NavHostController) {

}
