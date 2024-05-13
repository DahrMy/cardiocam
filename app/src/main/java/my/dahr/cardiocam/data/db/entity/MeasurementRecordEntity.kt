package my.dahr.cardiocam.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement_records")
data class MeasurementRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bpm: Int,
    val time: Long
)