package ep.udemy.a7minutesworkout

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface IHistoryDao {
    @Insert
    suspend fun insert (historyEntity: HistoryEntity)
}