package com.leonardo.businesscard

import android.app.Application
import com.leonardo.businesscard.data.AppDatabase
import com.leonardo.businesscard.data.BusinessCardRepository

class App : Application() {
    val database by lazy {AppDatabase.getDatabase(this)}
    val repository by lazy {BusinessCardRepository(database.businessDao())}
}