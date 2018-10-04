package com.msa.reduxed.data.cloud

import com.msa.reduxed.domain.entitiy.GithubSearchResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun search(
            @Query("q") query: String,
            @Query("sort") sort: String,
            @Query("page") page: Int
    ): Single<GithubSearchResults>
}
