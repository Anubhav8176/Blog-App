package com.anucodes.blogapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.anucodes.blogapp.model.Blog
import com.anucodes.blogapp.ui.theme.backContainer
import com.anucodes.blogapp.ui.theme.greatFont
import com.anucodes.blogapp.ui.theme.topContainer
import com.anucodes.blogapp.ui.theme.topTitle40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBlog(navController: NavController, viewModel: MainViewModel = MainViewModel()){
    var title by remember {
        mutableStateOf("")
    }
    var blogText by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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

    ){values->
        Card(
            modifier = Modifier
                .padding(values),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(backContainer)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    label = { Text("Enter the title!") },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    onValueChange = { title = it },
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = blogText,
                    label = { Text("Enter the blog!") },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    onValueChange = { blogText = it },
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 10
                )

                Spacer(modifier = Modifier.size(50.dp))

                Button(
                    onClick = {
                        val data = Blog(null ,title, blogText)
                        viewModel.postData(data)
                        navController.navigate("mainPage")
                    },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Add Blog!")
                }
            }
        }
    }
}