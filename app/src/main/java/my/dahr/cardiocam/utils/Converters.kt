package my.dahr.cardiocam.utils

import my.dahr.cardiocam.data.db.entity.MeasurementRecordEntity
import my.dahr.cardiocam.data.model.MeasurementRecord

fun MeasurementRecord.toEntity() = MeasurementRecordEntity(
    bpm = this.bpm,
    time = this.timestamp
)

fun MeasurementRecordEntity.toModel() = MeasurementRecord(
    bpm = this.bpm,
    timestamp = this.time
)