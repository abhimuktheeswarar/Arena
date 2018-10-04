package com.msa.reduxed.domain.entitiy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubSearchResults(
        val items: List<GithubRepository>
)
