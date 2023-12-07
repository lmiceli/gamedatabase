package com.lmiceli.gamedatabase.data.remote.https

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest(): Request {
        Timber.i("authorizing request with constants values")

        return newBuilder()
            .apply {
                this.header(AuthConstants.HEADER_CLIENT_ID, AuthConstants.HEADER_CLIENT_ID_VALUE)
                this.header(
                    AuthConstants.HEADER_AUTHORIZATION,
                    AuthConstants.HEADER_AUTHORIZATION_VALUE
                )
            }
            .build()
    }
}
