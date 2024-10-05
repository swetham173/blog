package com.example.blog

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BlogListScreen(navController: NavController, viewModel: BlogViewModel) {
    val blogPosts by viewModel.blogPosts.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val currentPage = viewModel.currentPage.collectAsState(initial = 1).value // Get the current page number

    if (errorMessage != null) {
        Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(blogPosts) { post ->
                BlogPostCard(post) {
                    navController.navigate("blog_detail/${post.id}")
                }
            }

            if (blogPosts.isNotEmpty()) {
                item {
                    PaginationControls(viewModel, currentPage)
                }
            }
        }
    }
}

@Composable
fun PaginationControls(viewModel: BlogViewModel, currentPage: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomButton(
            text = "Previous",
            onClick = { if (currentPage > 1) viewModel.fetchBlogPosts(currentPage - 1) },
            enabled = currentPage > 1
        )

        Text(
            text = "Page $currentPage",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp) // Centered with weight
        )

        CustomButton(
            text = "Next",
            onClick = { viewModel.fetchBlogPosts(currentPage + 1) },
            enabled = true
        )
    }
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit, enabled: Boolean) {
    val backgroundColor = if (enabled) Color.LightGray else Color.Gray
    val textColor = if (enabled) Color.Black else Color.White

    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(text = text, color = textColor, textAlign = TextAlign.Center)
    }
}

@Composable
fun BlogPostCard(post: BlogPost, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = post.title.rendered,
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp
                )
            )
        }
    }
}
