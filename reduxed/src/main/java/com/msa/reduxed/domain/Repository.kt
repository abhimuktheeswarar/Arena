package com.msa.reduxed.domain

import com.msa.reduxed.domain.entitiy.GithubSearchResults
import io.reactivex.Single

interface Repository {

    fun searchGithubRepositories(query: String, sort: String, page: Int): Single<GithubSearchResults>
}