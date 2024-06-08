package com.anucodes.blogapp

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageCard(
    viewModel: MainViewModel,
    item : Int,
    title: String, 
    description: String,
    modifier: Modifier
){
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    val applicationContext = LocalContext.current
    Log.i("The item $item", "The data")
    var showDialog by remember {
        mutableStateOf(false)
    }
    
    if (showDialog){
        DeleteDialog(
            item = item,
            onConfirm = {
                showDialog = false
                viewModel.deleteBlog(item)
            },
            onDismiss = {
                showDialog = false
            })
    }

    Card(
        modifier= modifier
            .fillMaxHeight(0.5f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog = true
                    },
                    onTap = {
                        val intent = Intent(applicationContext, ShowContent::class.java).apply {
                            putExtra("title", title)
                            putExtra("desc", description)
                        }
                        applicationContext.startActivity(intent)
                    }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium
    ){
        Image(painter = rememberAsyncImagePainter(
            model ="https://picsum.photos/seed/${Random.nextInt(1, 300)}/300/200")
            , contentDescription = "Random image loaded from the internet",
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(2.5f / 1.5f),
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = modifier.padding(1.dp))

        Text(text = description,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            modifier = modifier.padding(5.dp),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis)

        FlowRow (
            modifier = modifier
                .padding(5.dp)
                .fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            AssistChip(onClick = {
            },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.White,
                    leadingIconContentColor = Color.Red
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription ="Add to favourite")
                },
                label = {
                    Text(text = "Mark Favourite",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black)
                })

            AssistChip(onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "type/text"
                intent.putExtra(Intent.EXTRA_TEXT, title)
                intent.putExtra(Intent.EXTRA_TEXT, description)
                launcher.launch(Intent.createChooser(intent, "Share via"))
            },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.White,
                    leadingIconContentColor = Color.Black
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription ="Share the content",
                        Modifier.size(AssistChipDefaults.IconSize))
                },
                label = {
                    Text(text = "Share It!",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black)
                })
        }
    }
}


@Composable
fun DeleteDialog(
    item: Int,
    onConfirm: ()->Unit,
    onDismiss: ()->Unit
) {
    AlertDialog(
        title = {
                Text(text = "Delete")
        },
        text = {
               Text(text = "Do you want to delete the blog?")
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Dismiss")
            }
        })

}
