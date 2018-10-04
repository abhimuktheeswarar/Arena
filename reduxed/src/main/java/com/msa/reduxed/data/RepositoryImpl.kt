package com.msa.reduxed.data

import com.msa.reduxed.data.cloud.GithubApi
import com.msa.reduxed.domain.Repository
import com.msa.reduxed.domain.entitiy.GithubSearchResults
import io.reactivex.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val githubApi: GithubApi) : Repository {

    override fun searchGithubRepositories(query: String, sort: String, page: Int): Single<GithubSearchResults> {
        return githubApi.search(
                query = "language:java",
                page = page,
                sort = "stars"
        )
    }
}