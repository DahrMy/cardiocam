package my.dahr.cardiocam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import my.dahr.cardiocam.data.db.dao.MeasurementRecordDao
import my.dahr.cardiocam.data.db.entity.MeasurementRecordEntity

@Database(
    entities = [
        MeasurementRecordEntity::class
        // other entities
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun measurementRecordDao(): MeasurementRecordDao

}