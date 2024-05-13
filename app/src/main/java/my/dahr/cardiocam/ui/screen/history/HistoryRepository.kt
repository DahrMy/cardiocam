package my.dahr.cardiocam.ui.screen.history

import my.dahr.cardiocam.data.db.dao.MeasurementRecordDao
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    val dao: MeasurementRecordDao
) {

    suspend fun clearAllRecordings() {
        dao.clearTable()
    }

    fun getAllRecordingsFlow() = dao.getAllEntities()

}