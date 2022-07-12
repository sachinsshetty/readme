package com.slabstech.readme

import com.slabstech.readme.AppConfiguration
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.slabstech.readme.github.GithubUser
import com.slabstech.readme.github.GithubServiceGenerator
import com.slabstech.readme.github.GithubUserService

class App : Application<AppConfiguration?>() {
    override fun getName(): String {
        return "App"
    }

    override fun initialize(bootstrap: Bootstrap<AppConfiguration?>) {
        // TODO: application initialization
    }

    override fun run(
        configuration: AppConfiguration?,
        environment: Environment
    ) {
        // TODO: implement application
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            App().run(*args)

            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build()

        // Using GitHubServiceGenerator
        var service = GithubServiceGenerator.createService(GithubUserService::class.java)
        val callSync: Call<GithubUser?>? = service.getGithubUser("sachinsshetty")
        val callAsync: Call<GithubUser?>? = service.getGithubUser("sachinsshetty")
        try {
            val response: Response<GithubUser?> = (callSync?.execute() ?: null) as Response<GithubUser?>
            val GithubUser: GithubUser? = response.body()
            println(GithubUser)
        } catch (ex: IOException) {
        }

        // Execute the call asynchronously. Get a positive or negative callback.
        if (callAsync != null) {
            callAsync.enqueue(object : Callback<GithubUser?> {
                override fun onResponse(call: Call<GithubUser?>?, response: Response<GithubUser?>) {
                    val GithubUser: GithubUser? = response.body()
                    System.out.println(GithubUser)
                }

                override fun onFailure(call: Call<GithubUser?>?, throwable: Throwable?) {
                    println(throwable)
                }
            })
        }
        }
    }
}