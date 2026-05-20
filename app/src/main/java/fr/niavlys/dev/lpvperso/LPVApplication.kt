package fr.niavlys.dev.lpvperso

import android.app.Application
import androidx.room.Room
import fr.niavlys.dev.lpvperso.data.AppDatabase
import fr.niavlys.dev.lpvperso.data.AppRepository

class LPVApplication : Application() {
    lateinit var repository: AppRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "lpv-perso.db",
        )
            .fallbackToDestructiveMigration(true)
            .build()
        repository = AppRepository(database)
    }
}
