package com.example.mizuho.coroutinetest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG: String = "TEST_MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        main1()
        Log.d(TAG, "main1 end")
        Thread.sleep(3000L)     // メインスレッドで待つ
        main2()
        Log.d(TAG, "main2 end")
        Thread.sleep(3000L)     // メインスレッドで待つ
        main3()
        Log.d(TAG, "main3 end")
        main4()
        Log.d(TAG, "main4 end")



    }

    // GlobalScope.launch
    fun main1() {
        Log.d(TAG, "main1 start")
        GlobalScope.launch {        // バックグラウンドで新しくコルーチンを起動
            delay(1000L)  // このコルーチン内で1秒待つ
            Log.d(TAG, "main1 GlobalScope.launch")
        }
        Log.d(TAG, "main1 GlobalScope.launch end")
        Thread.sleep(2000L)     // メインスレッドで待つ
    }

    // runBlocking
    fun main2() {
        Log.d(TAG, "main2 start")
        runBlocking {// コルーチン内の処理が終わるまでメインスレッドの処理も中断
            Log.d(TAG, "main2 runBlocking!")
            delay(2000L)
        }
        Log.d(TAG, "main2 runBlocking! end")
    }

    fun main3() = runBlocking<Unit> {   // start main coroutine
        Log.d(TAG, "main3 start")
        GlobalScope.launch {            // launch new coroutine in background and continue
            delay(1000L)
            Log.d(TAG, "main3 launch")
        }
        Log.d(TAG, "main3 launch end")            // main coroutine continues here immediately
        delay(2000L)          // delaying for 2 seconds to keep JVM alive
    }


    fun main4() = runBlocking {
        Log.d(TAG, "main4 start")
        val job = GlobalScope.launch { // launch new coroutine and keep a reference to its Job
            delay(1000L)
            Log.d(TAG, "main4 coroutin")
        }
        Log.d(TAG, "main4 coroutin end")
        job.join() // コルーチンが終了するまで待つ
    }
}
