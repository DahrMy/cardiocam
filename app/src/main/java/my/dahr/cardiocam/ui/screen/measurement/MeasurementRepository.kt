package my.dahr.cardiocam.ui.screen.measurement

import my.dahr.cardiocam.data.db.dao.MeasurementRecordDao
import my.dahr.cardiocam.data.model.MeasurementRecord
import my.dahr.cardiocam.utils.toEntity
import javax.inject.Inject

class MeasurementRepository @Inject constructor(
    val dao: MeasurementRecordDao
) {

    suspend fun insertRecordIntoDb(record: MeasurementRecord) {
        dao.insertRecord(record.toEntity())
    }

}