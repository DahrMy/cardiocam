package my.dahr.cardiocam.hrometer.camera

import androidx.annotation.IntDef

@Target(AnnotationTarget.TYPE)
@IntDef(0, 90, 180, 270)
@Retention(AnnotationRetention.SOURCE)
annotation class CameraOrientation