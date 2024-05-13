package my.dahr.cardiocam.data.model

import android.os.Parcel
import android.os.Parcelable

data class MeasurementRecord(
    val bpm: Int,
    val timestamp: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bpm)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MeasurementRecord> {
        override fun createFromParcel(parcel: Parcel): MeasurementRecord {
            return MeasurementRecord(parcel)
        }

        override fun newArray(size: Int): Array<MeasurementRecord?> {
            return arrayOfNulls(size)
        }
    }
}
