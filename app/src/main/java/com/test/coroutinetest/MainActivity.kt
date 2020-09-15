package com.test.coroutinetest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
