package com.djordje.concurency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.djordje.concurency.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis


const val TAG = "MainActivty"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Base example

//
//        GlobalScope.launch {
//            Log.d(
//                TAG, "Coroutine says hello from ${Thread.currentThread().name}"
//            )
//            val answer1 = doNetworkCall()
//            Log.d(TAG, "In between")
//            val answer2 = doNetworkCall2()
//
//            Log.d(TAG, "Answer from netwrok fun 1 $answer1")
//            Log.d(TAG, "Answer from netwrok fun 2 $answer2")
//        }
//
//
//        Log.d(TAG, "Hello from ${Thread.currentThread().name}")


        //Thread switch example

//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "Coroutine starts in thread ${Thread.currentThread().name}")
//            val answer1 = doNetworkCall()
//            withContext(Dispatchers.Main) {
//                Log.d(TAG, "Coroutine continues in thread ${Thread.currentThread().name}")
//                binding.tvDumyy.text = answer1
//            }
//        }


        //Runblocking

//        runBlocking {
//            Log.d(TAG, "Starts runblocking")
//            launch(Dispatchers.IO) {
//                delay(2000L)
//                Log.d(TAG, "Finished IO couroutine 1")
//            }
//            delay(2000L)
//            Log.d(TAG, "Finished IO coroutine 2")
//        }

        //Jobs, waiting, canceling


//        val job = GlobalScope.launch(Dispatchers.Default) {
//            repeat(5) {
//                Log.d(TAG, "Coroutine still running")
//                delay(2000L)
//            }
//        }

//        val job = GlobalScope.launch(Dispatchers.Default) {
//            Log.d(TAG, "Long running operation started")
//            withTimeout(3000) {
//                for (i in 30..45) {
//                    if (isActive) {
//                        Log.d(TAG, "Fibonacci sequence element for i = $i: ${fib(i)}")
//                    }
//                }
//            }
//            Log.d(TAG, "Long running operation ending")
//        }
//
////        runBlocking {
////            delay(2000)
//////            job.join()
////            Log.d(TAG, "Main thread contiues")
////            job.cancel()
////            Log.d(TAG, "Job is canceled")
////        }

        //ASYNC AWAIT

//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//
//                //Primjer izvrsavanje 6 sekundi
////                var answer1 = doNetworkCall()
////                var answer2 = doNetworkCall2()
//
//                //Primjer izvrsavanje 3 sekunde
////                var answer1: String? = null
////                var answer2: String? = null
////
////                val job = launch { answer1 = doNetworkCall() }
////                val job2 = launch { answer2 = doNetworkCall2() }
////
////                job.join()
////                job2.join()
//
//                val answer1 = async {
//                    doNetworkCall()
//                }
//
//                val answer2 = async {
//                    doNetworkCall2()
//                }
//
//                Log.d(TAG, "Answer 1 is ${answer1.await()}")
//                Log.d(TAG, "Answer 2 is ${answer2.await()}")
//            }
//
//            Log.d(TAG, "Request took $time")
//
//        }


        //Lifecycle scope

        binding.btnStartActivity.setOnClickListener {
            lifecycleScope.launch {
                while (true) {
                    delay(1000L)
                    Log.d(TAG, "Still running..")
                }
            }
            GlobalScope.launch {
                delay(5000L)
                Intent(this@MainActivity, SecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }

    }

    fun fib(n: Int): Long {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fib(n - 1) + fib(n - 2)
        }
    }


    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer"
    }

    suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is the answer 2"
    }
}