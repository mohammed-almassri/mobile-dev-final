package com.example.thefinal.feature.jokes.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class JokeWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return runCatching {
            for (i in 1..50) {
                if (isStopped) {
                    Log.d("MyWorker", "Worker stopped")
                    return Result.failure()
                }
                println("Hello from worker $i")
                Thread.sleep(1000)
                Log.d("MyWorker", "Hello from worker $i")
            }
        }.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {error ->
                Result.failure()
            }
        )
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("JokeWorker", "Worker stopped")
    }
}