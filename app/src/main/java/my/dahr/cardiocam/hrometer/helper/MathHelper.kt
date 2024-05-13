package my.dahr.cardiocam.hrometer.helper

object MathHelper {
    //vvvvvvvvvvvv image processing vvvvvvvvvvvv
    private fun decodeYUV420SPtoRedSum(yuv420sp: ByteArray?, width: Int, height: Int): Int {
        if (yuv420sp == null) {
            return 0
        }

        val frameSize = width * height
        var sum = 0

        var heightIndex = 0
        var pixelIndex = 0
        while (heightIndex < height) {
            var uvp = frameSize + (heightIndex shr 1) * width
            var u = 0
            var v = 0

            var widthIndex = 0
            while (widthIndex < width) {
                var y = (0xff and yuv420sp[pixelIndex].toInt()) - 16

                if (y < 0) {
                    y = 0
                }

                if ((widthIndex and 1) == 0) {
                    v = (0xff and yuv420sp[uvp++].toInt()) - 128
                    u = (0xff and yuv420sp[uvp++].toInt()) - 128
                }

                val y1192 = 1192 * y

                var redPixel = (y1192 + 1634 * v)
                if (redPixel < 0) {
                    redPixel = 0
                } else if (redPixel > 262143) {
                    redPixel = 262143
                }

                var greenPixel = (y1192 - 833 * v - 400 * u)
                if (greenPixel < 0) {
                    greenPixel = 0
                } else if (greenPixel > 262143) {
                    greenPixel = 262143
                }

                var bluePixel = (y1192 + 2066 * u)
                if (bluePixel < 0) {
                    bluePixel = 0
                } else if (bluePixel > 262143) {
                    bluePixel = 262143
                }

                val pixel =
                    (-0x1000000 or ((redPixel shl 6) and 0xff0000) or ((greenPixel shr 2) and 0xff00)
                            or ((bluePixel shr 10) and 0xff))

                val red = (pixel shr 16) and 0xff
                sum += red
                widthIndex++
                pixelIndex++
            }
            heightIndex++
        }

        return sum
    }

    fun decodeYUV420SPtoRedAvg(yuv420sp: ByteArray?, width: Int, height: Int): Int {
        if (yuv420sp == null) {
            return 0
        }

        val frameSize = width * height

        val sum = decodeYUV420SPtoRedSum(yuv420sp, width, height)

        return (sum / frameSize)
    } //^^^^^^^^^^^^ image processing ^^^^^^^^^^^^

}