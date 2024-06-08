package com.anucodes.blogapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anucodes.blogapp.ui.theme.BlogAppTheme
import com.anucodes.blogapp.ui.theme.greatFont
import com.anucodes.blogapp.ui.theme.topContainer
import com.anucodes.blogapp.ui.theme.topTitle40

class ShowContent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlogAppTheme {
                val id = intent.getIntExtra("id", 0)
                val titleRecieved = intent.getStringExtra("title").toString()
                val descriptionRecieved = intent.getStringExtra("desc").toString()
                ShowContent(titleRecieved, descriptionRecieved, MainViewModel())
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowContent(titleReceived: String, descriptionReceived: String, viewModel: MainViewModel = MainViewModel()) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
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
    ) {values->
        Box (
            modifier = Modifier
                .padding(values)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = titleReceived,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = descriptionReceived,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                FlowRow(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AssistChip(onClick = {
                    },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White,
                            leadingIconContentColor = Color.Red
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Add to favourite"
                            )
                        },
                        label = {
                            Text(
                                text = "Mark Favourite",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black
                            )
                        })

                    AssistChip(onClick = {
                        val intent1 = Intent(Intent.ACTION_SEND)
                        intent1.type = "type/text"
                        intent1.putExtra(Intent.EXTRA_TEXT, titleReceived)
                        intent1.putExtra(Intent.EXTRA_TEXT, descriptionReceived)
                        launcher.launch(Intent.createChooser(intent1, "Share via"))
                    },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color.White,
                            leadingIconContentColor = Color.Black
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Share the content",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        },
                        label = {
                            Text(
                                text = "Share It!",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Black
                            )
                        })
                }
            }
        }
    }
}

