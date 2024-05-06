package com.application.notestest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.application.notestest.room.DatabaseBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DatabaseBuilder.getInstance(context)
    }

    override fun onTerminate() {
        super.onTerminate()

        ioScope.cancel()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var ioScope = CoroutineScope(Dispatchers.IO)
    }
}