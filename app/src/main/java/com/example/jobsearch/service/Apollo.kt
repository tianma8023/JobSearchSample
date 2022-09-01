package com.example.jobsearch.service

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.apollographql.apollo3.network.http.LoggingInterceptor.Level.BODY

private const val SERVE_URL = "https://api.graphql.jobs"

val apolloClient: ApolloClient by lazy {
    ApolloClient.Builder()
        .addHttpInterceptor(LoggingInterceptor(BODY) {
            Log.d("ApolloClient", it)
        })
        .serverUrl(SERVE_URL)
        .build()
}