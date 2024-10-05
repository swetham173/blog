package com.example.blog

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BlogNavigation(navController: NavHostController, viewModel: BlogViewModel) {
    NavHost(navController = navController, startDestination = "blog_list") {
        composable("blog_list") {
            BlogListScreen(navController, viewModel)
        }
        composable("blog_detail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            postId?.let { BlogDetailScreen(it) }
        }
    }
}
