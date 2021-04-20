package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
	@ExperimentalTime
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		//
		//sayHello()
		//printHello()
		mainOne()
	}

	private fun sayHello() = runBlocking {
		val job = GlobalScope.launch {
			delay(5000)
			println("World!")
		}
		println("Hello,")
		job.join() // wait until child coroutine completes
		println("Kotlin")
	}


	private fun printHello() = runBlocking {
		val job = launch {
			repeat(1000) { i ->
				println("I'm sleeping $i......")
				delay(500L)
			}
		}
		delay(1300L) // delay a bit
		println("main: I'm tired of waitting!")
		job.cancel()
		println("main: Now, I can quit.")
	}

	fun mainOne() = runBlocking {
		val startTime = System.currentTimeMillis()
		val job = launch(Dispatchers.Default){
			var nextPrintTime = startTime
			var i = 0
			while (isActive) { // Dieu kien i < 5 duoc thay bang isActive de ngan chan coroutine khi no bi huy
				if (System.currentTimeMillis() >= nextPrintTime) {
					println("job: I'm sleeping ${i++}...")
					nextPrintTime += 500L
				}
			}
		}
		delay(1300L)// delay a bit
		println("main: I'm tired of waitting")
		job.cancel()
		println("main: Now I can quit.")
	}
}