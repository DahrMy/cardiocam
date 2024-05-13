package my.dahr.cardiocam.utils

import my.dahr.cardiocam.data.db.entity.MeasurementRecordEntity
import my.dahr.cardiocam.data.model.MeasurementRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun MeasurementRecord.toEntity() = MeasurementRecordEntity(
    bpm = this.bpm,
    time = this.timestamp
)

fun MeasurementRecordEntity?.toModel(): MeasurementRecord? {
    return if (this != null) {
        MeasurementRecord(bpm = this.bpm, timestamp = this.time)
    } else null
}

fun List<MeasurementRecordEntity>.toModels(): MutableList<MeasurementRecord> {
    val list = mutableListOf<MeasurementRecord>()
    this.forEach { list.add(it.toModel()!!) }
    return list
}

fun timestampToDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = Date(timestamp)

    return dateFormat.format(date)
}

fun timestampToTime(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
    val date = Date(timestamp)

    return dateFormat.format(date)
}