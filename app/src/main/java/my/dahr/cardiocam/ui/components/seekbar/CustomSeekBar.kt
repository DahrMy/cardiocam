package my.dahr.cardiocam.ui.components.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar

class CustomSeekBar : AppCompatSeekBar {
    private lateinit var mProgressItemsList: ArrayList<ProgressItem>

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    fun initData(progressItemsList: ArrayList<ProgressItem>) {
        mProgressItemsList = progressItemsList
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        if (mProgressItemsList.size > 0) {

            val progressBarWidth = width
            val progressBarHeight = height
            val thumbOffset = thumbOffset
            var lastProgressX = 0
            var progressItemWidth: Int
            var progressItemRight: Int

            for (i in mProgressItemsList.indices) {
                val progressItem: ProgressItem = mProgressItemsList[i]
                val progressPaint = Paint()

                progressPaint.setColor(resources.getColor(progressItem.color, null))

                progressItemWidth = ((progressItem.progressItemPercentage
                        * progressBarWidth / 100).toInt())

                progressItemRight = lastProgressX + progressItemWidth

                // for last item give right to progress item to the width
                if (i == mProgressItemsList.size - 1 && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth
                }

                val progressRect = Rect()

                progressRect.set(
                    lastProgressX, thumbOffset / 2,
                    progressItemRight, progressBarHeight - thumbOffset / 2
                )

                canvas.drawRect(progressRect, progressPaint)
                lastProgressX = progressItemRight
            }

            super.onDraw(canvas)

        }
    }
}