package com.test.coroutinetest

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


//typealias Callback = (User) -> Unit

interface Callback<T> {
    fun onError(t: Throwable)
    fun onSuccess(user: T)
}

fun getUser(callback: Callback<User>) {
    val call = OkHttpClient().newCall(
        Request.Builder()
            .get().url("https://api.github.com/users/JeffrayZ")
            .build()
    )
    call.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            callback.onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.let {
                val result = it.string()
                log(result)
                try {
                    callback.onSuccess(Gson().fromJson(result, User::class.java))
                } catch (e: Exception) {
                    callback.onError(e) // 这里可能是解析异常
                }
            } ?: callback.onError(NullPointerException("ResponseBody is null."))
        }

    })
}

data class User(
    val avatar_url: String?,
    val bio: Any?,
    val blog: String?,
    val company: Any?,
    val created_at: String?,
    val email: Any?,
    val events_url: String?,
    val followers: Int?,
    val followers_url: String?,
    val following: Int?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val hireable: Any?,
    val html_url: String?,
    val id: Int?,
    val location: Any?,
    val login: String?,
    val name: Any?,
    val node_id: String?,
    val organizations_url: String?,
    val public_gists: Int?,
    val public_repos: Int?,
    val received_events_url: String?,
    val repos_url: String?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val twitter_username: Any?,
    val type: String?,
    val updated_at: String?,
    val url: String?
)

private class RunSuspend : Continuation<Unit> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    private var result: Result<Unit>? = null

    override fun resumeWith(result: Result<Unit>) = synchronized(this) {
        this.result = result
        (this as Object).notifyAll()
    }

    fun await() = synchronized(this) {
        while (true) {
            when (val result = this.result) {
                null -> (this as Object).wait()
                else -> {
                    result.getOrThrow() // throw up failure
                    return
                }
            }
        }
    }
}

internal fun runSuspend(block: suspend () -> Unit) {
    val run = RunSuspend()
    block.startCoroutine(run)
    run.await()
}


val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")
val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(msg: Any?) = println("${now()} [${Thread.currentThread().name}] $msg")