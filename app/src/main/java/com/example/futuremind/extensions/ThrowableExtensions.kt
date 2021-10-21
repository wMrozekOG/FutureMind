package com.example.futuremind.extensions

import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.BindException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.PortUnreachableException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import java.nio.channels.ClosedChannelException
import javax.net.ssl.SSLException

/**
 * Check if throwable is related with network issues
 */
fun Throwable.isNetworkException(): Boolean {
    return when (this) {
        is HttpException,
        is BindException,
        is ClosedChannelException,
        is ConnectException,
        is InterruptedIOException,
        is NoRouteToHostException,
        is PortUnreachableException,
        is ProtocolException,
        is SocketException,
        is SocketTimeoutException,
        is SSLException,
        is UnknownHostException,
        is UnknownServiceException -> {
            true
        }
        else -> {
            false
        }
    }
}