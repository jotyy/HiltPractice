package top.jotyy.hiltpractice.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.jotyy.hiltpractice.data.remote.ApiService

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}
