package com.test.coroutinetest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import okhttp3.*
import okhttp3.internal.cache.CacheInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test15?.setOnClickListener {

        }

        runSuspend {
            log(1)
            GlobalScope.launch {
                log(2)
            }.join()
            log(3)
        }

        test8.setOnClickListener {
            lifecycleScope.launch {
                Log.i("test8", "开始时间：${System.currentTimeMillis()}")
                val deferred1 = async {
                    delay(5000L)
                    1
                }
                val deferred3 = async {
                    delay(10000L)
                    3
                }
                val deferred2 = async {
                    delay(3000L)
                    2
                }

                val deferred4 = async {
                    deferred1
                }

                Toast.makeText(this@MainActivity, deferred2.await().toString(), Toast.LENGTH_SHORT).show()
                Log.i("test8", "${Thread.currentThread().name} ${deferred1.await()}")
                Log.i("test8", "${Thread.currentThread().name} ${deferred3.await()}")
                Log.i("test8", "${Thread.currentThread().name} ${deferred2.await()}")
//                Log.i("test8","${deferred1.await()}::${deferred2.await()}::${deferred3.await()}")
                Log.i("test8", "结束时间：${System.currentTimeMillis()}")
            }
        }

        test1.setOnClickListener {
            lifecycleScope.launch {
                try {
                    throw NullPointerException()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        test2.setOnClickListener {
            lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT)
                    .show()
            }) {
                throw NullPointerException()
            }
        }

        test3.setOnClickListener {
            val deferred = lifecycleScope.async {
                try {
                    throw NullPointerException()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        test4.setOnClickListener {
            lifecycleScope.async(CoroutineExceptionHandler { coroutineContext, throwable ->
                Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT)
                    .show()
            }) {
                throw NullPointerException()
            }
        }

        test5.setOnClickListener {
            /*lifecycleScope.launch {
                val deferred = async {
                    try {
                        throw NullPointerException()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT).show()
                    }
                }
                deferred.await()
            }*/


            // 有错误
            /*lifecycleScope.launch {
                try {
                    val deferred = async {
                        throw NullPointerException()
                    }
                    deferred.await()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT).show()
                }
            }*/
            // 有错误
        }

        test6.setOnClickListener {
//            lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
//                Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT).show()
//            }) {
//                val deferred = async {
//                    throw NullPointerException()
//                }
//                deferred.await()
//            }


            // 有错误
            /*lifecycleScope.launch {
                val deferred = async(CoroutineExceptionHandler { coroutineContext, throwable ->
                    Toast.makeText(this@MainActivity, "${(it as Button).text}", Toast.LENGTH_SHORT).show()
                }) {
                    throw NullPointerException()
                }
                deferred.await()
            }*/
            // 有错误
        }


        test7.setOnClickListener {
            /*lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Toast.makeText(this@MainActivity, "${throwable.message}", Toast.LENGTH_SHORT)
                    .show()
            }) {
                async {
//                    throw NullPointerException("NullPointerException") // 前后效果一样
                    launch {
                        throw IllformedLocaleException("IllformedLocaleException")
                        async {
                            throw BadParcelableException("BadParcelableException")
                        }
                    }
                    throw NullPointerException("NullPointerException")
                }
            }*/


            // 崩
            /*lifecycleScope.launch {
                try {
                    async {
                        launch {
                            throw IllformedLocaleException("IllformedLocaleException")
                            async {
                                throw BadParcelableException("BadParcelableException")
                            }
                        }
                        throw NullPointerException("NullPointerException")
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }*/


            // 崩
            /*lifecycleScope.launch {
                async(CoroutineExceptionHandler { coroutineContext, throwable ->
                    Toast.makeText(this@MainActivity, "${throwable.message}", Toast.LENGTH_SHORT)
                        .show()
                }) {
                    launch {
                        throw IllformedLocaleException("IllformedLocaleException")
                        async {
                            throw BadParcelableException("BadParcelableException")
                        }
                    }
                    throw NullPointerException("NullPointerException")
                }
            }*/


            // 崩
            /*lifecycleScope.launch {
                async {
                    launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                        Toast.makeText(
                            this@MainActivity,
                            "${throwable.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }) {
                        throw IllformedLocaleException("IllformedLocaleException")
                        async {
                            throw BadParcelableException("BadParcelableException")
                        }
                    }
                }
            }*/


            // 崩
            /*lifecycleScope.launch {
                async {
                    launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                        Toast.makeText(
                            this@MainActivity,
                            "${throwable.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }) {
                        async {
                            throw BadParcelableException("BadParcelableException")
                        }
                    }
                }
            }*/


            /*lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Toast.makeText(
                    this@MainActivity,
                    "${throwable.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }) {
                async {
                    launch {
                        async {
                            throw BadParcelableException("BadParcelableException")
                        }
                    }
                }
            }*/


            /*lifecycleScope.launch {
                val deferred = async {
                    try {
                        throw BadParcelableException("BadParcelableException")
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                deferred.await()
            }*/


            // 崩
            /*try {
                lifecycleScope.launch {
                    val deferred = async {
                        throw BadParcelableException("BadParcelableException")
                    }
                    deferred.await()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }*/


            /*lifecycleScope.launch {
                try {
                    throw  java.lang.NullPointerException("NullPointerException")
                } catch (e: Exception) {
                    Toast.makeText(
                        this@MainActivity,
                        "${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                val deferred = async {
                    try {
                        throw BadParcelableException("BadParcelableException")
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                deferred.await()

            }*/


            /*lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Toast.makeText(
                    this@MainActivity,
                    "${throwable.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }) {
                try {
                    throw java.lang.NullPointerException("NullPointerException")
                } catch (e: Exception) {
                    Toast.makeText(
                        this@MainActivity,
                        "${e.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                Toast.makeText(
                    this@MainActivity,
                    "出错继续执行",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val deferred = async {
                    throw BadParcelableException("BadParcelableException")
                }
                deferred.await()
            }*/


            /*lifecycleScope.launch {
//                testWithContext()
                testWithContext1()
            }*/



            lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                Log.e("testWithContext2", throwable.message.toString())
            }) {
                testWithContext2()
            }
        }

        test9?.setOnClickListener {
//            flow1()
//            flow2()


            /*lifecycleScope.launch() {
                val intFlow = flow {
                    (1..3).forEach {
                        log("里面》》》${it}")
                        emit(it)
                        delay(100)
                    }
                }
                intFlow.flowOn(Dispatchers.IO).collect{
                    log(it)
                }
                // 里面的worker线程执行，外面在main线程里面执行
            }*/


            // 捕获 Flow 的异常
            /*lifecycleScope.launch {
                flow {
                    emit(1)
                    throw ArithmeticException("Div 0")
                }.catch { t: Throwable ->
                    println("caught error: $t")
                }.collect()
            }*/



            // 订阅流的完成
            lifecycleScope.launch {
                flow {
                    throw ArithmeticException("Div 0")
                    emit(1)
                }.catch { t: Throwable ->
                    println("caught error: $t")
                }.onCompletion { t: Throwable? ->
                    println("finally.")
                }.collect{
                    log("最后>>>" + it)
                }
            }



            // Flow 从异常中恢复
            /*lifecycleScope.launch {
                flow {
                    emit(1)
                    throw ArithmeticException("Div 0")
                }.catch { t: Throwable ->
                    println("caught error: $t")
                    emit(10)
                }.collect()
            }*/



            /*val createFlow = flow<Int> {
                (1..3).forEach {
                    emit(it)
                    delay(100)
                }
            }.onEach { println(it) }
            createFlow.launchIn(lifecycleScope)*/




            // 不能在 Flow 中直接切换调度器
//            flow { // BAD!!
//                emit(1)
//                withContext(Dispatchers.IO){
//                    emit(2)
//                }
//            }

            // 想要在生成元素时切换调度器，就必须使用 channelFlow 函数来创建 Flow：
            /*channelFlow {
                send(1)
                withContext(Dispatchers.IO) {
                    send(2)
                }
            }*/

            // 此外，我们也可以通过集合框架来创建 Flow：
//            listOf(1, 2, 3, 4).asFlow()
//            setOf(1, 2, 3, 4).asFlow()
//            flowOf(1, 2, 3, 4)




            // 背压
            // 解决方式一、为 Flow 添加缓冲
            /*flow {
                List(100) {
                    emit(it)
                }
            }.buffer().onEach {
                log(it)
            }.launchIn(lifecycleScope)*/

            // 使用 conflate 解决背压问题
            // 与 Channel 的 Conflate 模式一致，新数据会覆盖老数据
            /*flow {
                List(100) {
                    emit(it)
                }
            }.conflate()
                .onEach { value ->
                    println("Collecting $value")
                    delay(100)
                    println("$value collected")
                }.launchIn(lifecycleScope)*/

            // 使用 collectLatest 解决背压问题
            // 它并不会直接用新数据覆盖老数据，而是每一个都会被处理，只不过如果前一个还没被处理完后一个就来了的话，处理前一个数据的逻辑就会被取消。
            // 除 collectLatest 之外还有 mapLatest、flatMapLatest 等等，都是这个作用。
            /*lifecycleScope.launch {
                flow {
                    List(100) {
                        emit(it)
                    }
                }.collectLatest{ value ->
                    println("Collecting $value")
                    delay(100)
                    println("$value collected")
                }
            }*/

            // 拼接的操作中 flattenConcat 是按顺序拼接的，结果的顺序仍然是生产时的顺序；
            // 还有一个是 flattenMerge，它会并发拼接，因此结果不会保证顺序。
            /*lifecycleScope.launch {
                flow {
                    List(5){ emit(it) }
                }.map {
                    flow { List(it) { emit(it) } }
                }.flattenConcat()
                    .collect { println(it) }
            }*/

        }

        test10?.setOnClickListener {
            lifecycleScope.launch {
                log(lifecycleScope.toString())
                log(this.toString())

//                test10_1()
                test10_2()
            }
        }

        test11?.setOnClickListener {
            lifecycleScope.launch {
//                test11_1()
                test11_2()
            }


            /*lifecycleScope.launch {
                val job1 = launch {
                    log(1)
                    val user = getCancellableUserCoroutine()
                    log(user)
                    log(2)
                }
                delay(10)
                log(3)
                job1.cancel()
                log(4)
            }*/
        }

        test12?.setOnClickListener {
            lifecycleScope.launch{
                val one = async{
                    delay(5000L)
                    3
                }

                val result = async{
                    one.await() + 5
                }

                log(result.await())
            }
        }

        test13?.setOnClickListener {
//            val fibonacci = sequence {
//                yield(1L) // first Fibonacci number
//                var cur = 1L
//                var next = 1L
//                while (true) {
//                    yield(next) // next Fibonacci number
//                    val tmp = cur + next
//                    cur = next
//                    next = tmp
//                }
//            }
//            fibonacci.take(5).forEach(::log)



            val seq = sequence {
                log("yield 1,2,3")
                yieldAll(listOf(1, 2, 3))
                log("yield 4,5,6")
                yieldAll(listOf(4, 5, 6))
                log("yield 7,8,9")
                yieldAll(listOf(7, 8, 9))
            }
            seq.take(5).forEach(::log)
        }

        test14.setOnClickListener {
            lifecycleScope.launch {
                launch(Dispatchers.IO) {
                    val url = "http://www.imooc.com/courseimg/s/cover005_s.jpg"
                    //配置缓存的路径，和缓存空间的大小
                    val cache = Cache(File(cacheDir,"tempppp"), 10 * 10 * 1024)
                    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .connectTimeout(15, TimeUnit.SECONDS) //打开缓存
                        .cache(cache)
                        .build()
                    val request: Request = Request.Builder()
                        .url(url) //request 请求单独配置缓存策略
                        //noCache()： 就算是本地有缓存，也不会读缓存，直接访问服务器
                        //noStore(): 不会缓存数据，直接访问服务器
                        //onlyIfCached():只请求缓存中的数据，不靠谱
                        .cacheControl(CacheControl.Builder().build())
                        .build()

                    CacheInterceptor

                    val call = okHttpClient.newCall(request)

                    val response = call.execute()

                    //读取数据
                    response.body!!.string()

                    println("network response:" + response.networkResponse)
                    println("cache response:" + response.cacheResponse)


                    //在创建 cache 开始计算
                    println("cache hitCount:" + cache.hitCount()) //使用缓存的次数
                    println("cache networkCount:" + cache.networkCount()) //使用网络请求的次数
                    println("cache requestCount:" + cache.requestCount()) //请求的次数
                }
            }
        }
    }

    suspend fun test11_2() {
        lifecycleScope.launch {
            println("test11_2 前")
            println(getUserCoroutine())
            println("test11_2 后")
        }
    }

    suspend fun test11_1() {
        lifecycleScope.launch {
            val job1 = launch { // ①
                log(1)
                try {
                    delay(1000) // ②
                } catch (e: Exception) {
                    log("cancelled. $e")
                }
                log(2)
            }
            delay(100)
            log(3)
            job1.cancel() // ③
            log(4)
        }
    }

    private suspend fun MainActivity.test10_2() {
        log(1)
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            log(2)
        }
        log(3)
        // 调用 Job.start，主动触发协程的调度执行
        // 调用 Job.join，隐式的触发协程的调度执行
        job.start()
        log(4)
    }

    private suspend fun MainActivity.test10_1() {
        log(1)
        val job = GlobalScope.launch {
            log(2)
        }
        log(3)
        // join确保在 log(4) 执行之前，执行完协程体里面的代码
        // 也就是log(4) 等待协程执行完毕后再执行
        job.join()
        log(4)
    }

    private fun flow2() {
        lifecycleScope.launch {
            flow {
                Log.d("test9", "${Thread.currentThread().name}:fllow")
                emit("${Thread.currentThread().name}:fllow")
            }.flowOn(Dispatchers.IO)
                .onEach {
                    Log.d("test9", "$it")
                    println(it)
                }
                .map {

                }
                .catch {
                    it.printStackTrace()
                }
                .onCompletion {
                    Log.d("test9", "${Thread.currentThread().name}:Complete")
                    println("${Thread.currentThread().name}:Complete")
                }
                .flowOn(Dispatchers.Main)
                .collect()
        }
    }

    private fun flow1() {
        lifecycleScope.launch {
            val ints: Flow<Int> = flow {
                for (i in 1..10) {
                    emit(i)
                    delay(1000)
                }
            }

            ints.collect {
                Log.d("test9", "$it")
            }
        }
    }

    fun <T> Flow<T>.buffer(size: Int = 0): Flow<T> = flow {
        coroutineScope {
            val channel = produce(capacity = size) {
                collect { send(it) }
            }
            channel.consumeEach { emit(it) }
        }
    }

    suspend fun testWithContext() {
        withContext(Dispatchers.IO) {
            try {
                throw java.lang.NullPointerException("NullPointerException")
            } catch (e: Exception) {
                Log.e("testWithContext", e.message.toString())
            }
        }
    }

    // 无效
    suspend fun testWithContext1() {
        withContext(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("testWithContext1", throwable.message.toString())
        }) {
            throw java.lang.NullPointerException("NullPointerException")

        }
    }

    suspend fun testWithContext2() {
        withContext(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("testWithContext22222", throwable.message.toString())
        }) {
            throw java.lang.NullPointerException("NullPointerException")

        }
    }


    suspend fun getUserCoroutine() = suspendCoroutine<User> { continuation ->
        getUser(object : Callback<User> {
            override fun onSuccess(user: User) {
                continuation.resume(user)
            }

            override fun onError(t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }

    suspend fun getCancellableUserCoroutine() = suspendCancellableCoroutine<User> { continuation ->
        val call = OkHttpClient().newCall(
            Request.Builder()
                .get().url("https://api.github.com/users/JeffrayZ")
                .build()
        )

        continuation.invokeOnCancellation { // ①
            log("invokeOnCancellation: cancel the request.")
            call.cancel()
        }

        call.enqueue(object : okhttp3.Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                log("onFailure: $e")
                continuation.resumeWithException(e)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                log("onResponse: ${response.code}")
                response.body?.let {
                    try {
                        continuation.resume(Gson().fromJson(it.string(), User::class.java))
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                } ?: continuation.resumeWithException(NullPointerException("ResponseBody is null."))
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        testSuspendContext()
//        testSuspendContext2()
//        println("lalala: ${System.currentTimeMillis()}")
    }

    fun testSuspendContext() = runBlocking {
        val job = launch {
            println("launch开始: ${System.currentTimeMillis()}")
            while (isActive) {
                delay(500)
                println("hello")
            }
            println("launch结束: ${System.currentTimeMillis()}")
        }
        val first = withContext(Dispatchers.Default) {
            var count = 0L
            println("first开始: ${System.currentTimeMillis()}   ${count}")
            repeat(1000) {
                count++
            }
            count.also {
                println("first结束: ${System.currentTimeMillis()}   ${it}")
            }
        }
        val second = withContext(Dispatchers.IO) {
            println("second开始: ${System.currentTimeMillis()}")
            delay(2000)
            2000.also {
                println("second结束: ${System.currentTimeMillis()}")
            }
        }

        println("first + second = ${first + second}")
        job.cancel()
    }


    fun testSuspendContext2() = runBlocking {
        println("first开始: ${System.currentTimeMillis()}")
        val first = withContext(Dispatchers.IO) {
            var count = 0L
            repeat(1000) {
                count++
            }
            delay(5000)
            count.also {
                println("count 结束: ${System.currentTimeMillis()}   ${it}")
            }
        }
        println("first结束: ${System.currentTimeMillis()}   ${first}")
    }
}

