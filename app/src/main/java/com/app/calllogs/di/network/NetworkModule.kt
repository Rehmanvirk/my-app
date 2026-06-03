package com.app.calllogs.di.network

import android.content.Context
import com.app.calllogs.di.repository.ActivitiesRepository
import com.app.calllogs.di.repository.ActivitiesRepositoryImpl
import com.app.calllogs.di.repository.ApiInterface
import com.app.calllogs.di.repository.AuthRepository
import com.app.calllogs.di.repository.AuthRepositoryImpl
import com.app.calllogs.di.repository.ChatRepository
import com.app.calllogs.di.repository.ChatRepositoryImpl
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.ContactsRepositoryImpl
import com.app.calllogs.di.repository.DealsRepository
import com.app.calllogs.di.repository.DealsRepositoryImpl
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.di.repository.LeadsRepositoryImpl
import com.app.calllogs.utils.TokenStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Change this to your base URL
    private const val BASE_URL = "http://192.168.10.42:3001/"//"https://agentdeskcrm.com/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides @Singleton
    fun provideTokenStore(@ApplicationContext ctx: Context) = TokenStore(ctx)

    @Provides
    @Singleton
    fun provideOkHttp(authInterceptor: AuthInterceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()

            .cookieJar(object : CookieJar {
                private val cookies = mutableListOf<Cookie>()

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    this.cookies.addAll(cookies)
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return cookies
                }
            })
            .addInterceptor(logger)
            .addInterceptor(authInterceptor)
//            .addInterceptor(CookieTokenExtractorInterceptor(tokenStore, cookieName = "access_token"))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiInterface): AuthRepository =
        AuthRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideLeadsRepository(api: ApiInterface): LeadsRepository =
        LeadsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideContactsRepository(api: ApiInterface): ContactsRepository =
        ContactsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDealsRepository(api: ApiInterface): DealsRepository =
        DealsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideChatRepository(api: ApiInterface): ChatRepository =
        ChatRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideActivitiesRepository(api: ApiInterface): ActivitiesRepository =
        ActivitiesRepositoryImpl(api)
}