package com.example.fdev.View.Design

import DesignViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.fdev.model.DesignResponse
import com.example.fdev.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeDesignScreen(navController: NavHostController, viewModel: DesignViewModel = viewModel()) {
    val context = LocalContext.current
    val designs by viewModel.designResponse.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val auth = FirebaseAuth.getInstance()
    val isDesigner = auth.currentUser?.displayName == "designerFdev"

    // Fetch designs when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.getDesigns()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {

        // Display Designs in a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f) // Ensure the grid takes available space
        ) {
            items(designs) { design ->
                DesignCard(design = design, navController = navController, isDesigner = isDesigner)
            }
        }
    }
}

@Composable
fun DesignCard(design: DesignResponse, navController: NavHostController, isDesigner: Boolean) {
    val imagePainter = rememberImagePainter(design.image)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 15.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(250.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set("design", design)
                        if (isDesigner) {
                            navController.navigate("PRODUCTDESIGNER")
                        } else {
                            navController.navigate("VIEWProductDetailsDesigner")
                        }
                    },
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = design.name,
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            color = Color(0xff606060)
        )
        Text(
            text = "$${design.price}",
            modifier = Modifier.padding(top = 4.dp),
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color(0xff606060)
        )
    }
}
