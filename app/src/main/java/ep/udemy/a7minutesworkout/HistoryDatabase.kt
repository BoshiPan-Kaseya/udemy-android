package ep.udemy.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): IHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        /**
         * @func: Check if the instance is null, then create instance and then return it
         * */
        fun getInstance(context: Context): HistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history-database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}