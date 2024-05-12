package my.dahr.heartrateometer.camera

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.view.SurfaceHolder

class CameraSupportImpl internal constructor(context: Context) : CameraSupport() {

    private var camera: CameraDevice? = null

    private var cameraIndex: Int = 0

    private var cameraId: String? = null

    var flashLightEnabled: Boolean = false

    private val manager: CameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    @SuppressLint("MissingPermission")
    override fun open(cameraId: Int): CameraSupport {
        try {
            val cameraIds = manager.cameraIdList

            this.cameraIndex = cameraId
            this.cameraId = cameraIds[cameraId]
            this.cameraId?.let { id ->
                manager.openCamera(id, object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice) {
                        this@CameraSupportImpl.camera = camera
                    }

                    override fun onDisconnected(camera: CameraDevice) {
                        this@CameraSupportImpl.camera = camera
                    }

                    override fun onError(camera: CameraDevice, error: Int) {
                        this@CameraSupportImpl.camera = camera
                        log(error.toString())
                    }
                }, null)
            }

        } catch (e: Exception) {
            log(e)
        }

        return this
    }

    override fun getOrientation(cameraId: Int): @CameraOrientation Int {
        return try {
            val cameraIds = manager.cameraIdList
            val characteristics = manager.getCameraCharacteristics(cameraIds[cameraId])
            characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        } catch (e: CameraAccessException) {
            log(e)
            0
        }
    }

    override fun setDisplayOrientation(orientation: @CameraOrientation Int) {
    }

    override fun setPreviewDisplay(holder: SurfaceHolder?) {

    }

    override fun setPreviewCallback(previewCallback: Camera.PreviewCallback?) {
    }

    override fun startPreview() {
    }

    override fun stopPreview() {
    }

    override fun release() {
    }

    override fun hasFlash(): Boolean {
        return false
    }

    override fun setFlash(flashMode: Int): Boolean {

        val enabled = !flashLightEnabled

        try {
            cameraId?.let { manager.setTorchMode(it, enabled) }
            flashLightEnabled = enabled
        } catch (e: CameraAccessException) {
            log(e)
        }

        return flashLightEnabled
    }
}