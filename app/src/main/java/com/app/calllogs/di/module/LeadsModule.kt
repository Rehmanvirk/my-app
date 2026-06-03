package com.app.calllogs.di.module

import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.di.repository.LeadsRepositoryImpl
import com.app.calllogs.di.repository.facke.FakeLeadsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LeadsModule {

//    @Binds
//    @Singleton
//    abstract fun bindLeadsRepo(impl: LeadsRepositoryImpl): LeadsRepository
}