package com.msa.reduxed.domain.entitiy

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubRepository(
        val id: Long,
        val name: String,
        @Json(name = "stargazers_count") val stars: Long
)
