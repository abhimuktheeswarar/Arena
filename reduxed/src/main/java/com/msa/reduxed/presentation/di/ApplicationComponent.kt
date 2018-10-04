package com.msa.reduxed.presentation.di

import com.msa.reduxed.presentation.popularrepo.PopularReposActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(into: PopularReposActivity)
}
