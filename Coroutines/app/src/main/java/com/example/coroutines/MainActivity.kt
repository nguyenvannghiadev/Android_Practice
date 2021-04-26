package com.example.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime

class MainActivity : AppCompatActivity() {
	@ExperimentalTime
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		//
		//sayHello()
		//printHello()
		//mainOne()
		//mainTwo()
		//mainThree()
		//mainFour()
		//mainFive()
		//mainSix()
		mainSeven()
		main8()

	}

	private fun main8() = runBlocking {
		val time2 = measureTimeMillis {
			val one = async { printlnOne() }
			val two = async { printlnTwo() }
			println("The answer is ${one.await() + two.await()}")
		}
		println("Oncomplete 2 in $time2 s")
	}

	private fun mainSeven() = runBlocking<Unit> {
		val time = measureTimeMillis {
			val one = printlnOne()
			val two = printlnTwo()
			println("The answer is ${one + two}")

		}
		println("Complete in $time s")

	}

	suspend fun printlnOne(): Int {
		delay(1000L)
		return 10
	}

	suspend fun printlnTwo(): Int {
		delay(2000L)
		return 20
	}


	private fun mainSix() = runBlocking {
		val job = launch {
			try {
				repeat(1000) { i ->
					println("I'm sleeping $i.....")
					delay(500L)

				}
			}finally {
			    // tranh thu close resource nay di
				println("I'm running finally")
			}
		}
		delay(1300L)
		println("main: I'm tired of waitting")
		job.cancel()
		println("main: Now, I can quit")
	}

	private fun mainFive() = runBlocking {
		launch(Dispatchers.Unconfined){
			println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
			delay(1000L)
			println("unconfined: After delay in thread ${Thread.currentThread().name}")
		}
	}

	private fun mainFour() = runBlocking<Unit> {
		launch(Dispatchers.Unconfined) {
			println("Unconfined: I'm working in theard ${Thread.currentThread().name}")
		}

		launch(Dispatchers.Default){
			println("Default: I'm working in thread ${Thread.currentThread().name}")
		}

		launch(newSingleThreadContext("MyOwnThread")){
			println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}" )
		}

	}

	private fun mainThree() {
		GlobalScope.launch(Dispatchers.IO) {

			// Do background task
			withContext(Dispatchers.Main){
				// Update UI
			}

		}
	}

	private fun mainTwo() {
		newSingleThreadContext("thread1").use { ctx1 ->
			println("ctx1 - ${Thread.currentThread().name}")

			newSingleThreadContext("thread2").use { ctx2 ->
				println("ctx2 - ${Thread.currentThread().name}")

				//bat dau chay coroutines voi context la ctx1
				runBlocking(ctx1) {
					//coroutines dang chay tren context ctx1 va thread thread1
					println("Start in ctx1 - ${Thread.currentThread().name}")
					//su dung ham withContext de chuyen doi context tu ctx1 -> ctx2
					withContext(ctx2) {
						// coroutines dang chay voi context ctx2 va tren thread2
						println("Working in ctx2 - ${Thread.currentThread().name}")
						// coroutine đã thoát ra block withContext nên sẽ chạy lại với context ctx1 và trên thread thread1
						println("Back to ctx1 - ${Thread.currentThread().name}")

					}
				}
			}
			println("Out to ctx2 block -${Thread.currentThread().name}")
		}
		println("Out to ctx1 block -${Thread.currentThread().name}")
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