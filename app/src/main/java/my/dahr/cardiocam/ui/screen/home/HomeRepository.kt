package my.dahr.cardiocam.ui.screen.home

import my.dahr.cardiocam.data.db.dao.MeasurementRecordDao
import javax.inject.Inject

class HomeRepository @Inject constructor(
    val dao: MeasurementRecordDao
) {

    suspend fun getLastMeasurementRecord() = dao.getLastEntry()

}
