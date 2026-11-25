package com.serxanallahverdiyev.movieapp.presentation.search

import com.serxanallahverdiyev.movieapp.domain.model.SearchResult

data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val searchResults: List<SearchResult> = emptyList(),
    val error: String? = null
)

sealed interface SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent
    data object Search : SearchEvent
}
