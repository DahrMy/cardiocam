package my.dahr.cardiocam.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import my.dahr.cardiocam.data.db.entity.MeasurementRecordEntity

@Dao
interface MeasurementRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecord(entity: MeasurementRecordEntity)

    @Query("SELECT * FROM measurement_records ORDER BY time DESC")
    fun getAllEntities(): Flow<List<MeasurementRecordEntity>>

    @Query("SELECT * FROM measurement_records ORDER BY time DESC LIMIT 1")
    suspend fun getLastEntry(): MeasurementRecordEntity

    @Query("DELETE FROM measurement_records")
    suspend fun clearTable()

}