package sopt_jungnami_android.jungnami.Network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application() {
    lateinit var networkService: NetworkService

    private val baseUrl = "https://jungnami.ml/"
    companion object {
        lateinit var instance : ApplicationController
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    fun buildNetwork(){

        val builder = Retrofit.Builder()
        val retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        networkService = retrofit.create(NetworkService::class.java)
    }
}