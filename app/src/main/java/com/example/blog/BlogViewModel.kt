package com.example.blog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BlogViewModel : ViewModel() {
    private val blogApi = BlogApiService.create()
    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts: StateFlow<List<BlogPost>> = _blogPosts


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    init {
        fetchBlogPosts(1)
    }

    fun fetchBlogPosts(page: Int) {
        viewModelScope.launch {
            try {
                val blogs = blogApi.getBlogPosts(perPage = 10, page = page)
                _blogPosts.value = blogs
                _currentPage.value = page
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
