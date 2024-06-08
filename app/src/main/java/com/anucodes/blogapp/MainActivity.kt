package com.anucodes.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anucodes.blogapp.ui.theme.BlogAppTheme
import com.anucodes.blogapp.ui.theme.backContainer
import com.anucodes.blogapp.ui.theme.greatFont
import com.anucodes.blogapp.ui.theme.topContainer
import com.anucodes.blogapp.ui.theme.topTitle40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogAppTheme {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "mainPage"){
                    composable("mainPage"){
                        MainPage(navController, viewModel = MainViewModel())
                    }
                    composable("addBlog"){
                        AddBlog(navController, MainViewModel())
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(navController: NavController, viewModel: MainViewModel = MainViewModel()){
    val blogs = viewModel.blogs

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backContainer
    ) {
        Scaffold(
            modifier = Modifier
                .padding(10.dp),
            containerColor = backContainer,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("addBlog")
                },
                    containerColor = topTitle40
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = topContainer,
                        titleContentColor = topTitle40
                    ),
                    title = {
                        Text(text = "Blog App",
                            fontFamily = greatFont,
                            fontSize = 30.sp
                        )
                    })
            }
        ) {values->
            LazyColumn(contentPadding = values) {
                items(blogs.size){it->
                    ImageCard(
                        viewModel,
                        item = it,
                        title = blogs[it].title,
                        description = blogs[it].content,
                        modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}