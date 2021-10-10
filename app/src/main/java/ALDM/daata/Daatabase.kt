package ALDM.daata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Daata::class], version = 2, exportSchema = false)
abstract class Daatabase : RoomDatabase() {
    abstract fun timeDao() : tDao
    companion object{
        @Volatile
        private var instance: Daatabase? = null
        // singleton
        fun getInstance(context:Context): Daatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): Daatabase {
            return Room.databaseBuilder(
                context,
                Daatabase::class.java,
                "alarm_database.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

}