package se.lohnn.imageviewer.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.ImageView

/**
 * Copyright (C) lohnn on 2016.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class ImageViewerView(context: Context?) : ImageView(context), SensorEventListener {
    companion object {
        final val TAG = "ImageViewerView"
    }

    private var bitmap: Bitmap? = null
    private var imageRect = Rect()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        bitmap = (drawable as BitmapDrawable).bitmap
    }


    fun setSensorManager(sensorManager: SensorManager) {
        val senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //        throw UnsupportedOperationException()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //        throw UnsupportedOperationException()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        Log.d(TAG, "In onMeasure")

        if (drawable == null) return

        val imageRatio = drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth
        val screenRatio = measuredWidth.toFloat() / measuredHeight

        //Image ratio is wider than screen ratio
        if (imageRatio > screenRatio) {
            //Use height
            val imageHeight = measuredHeight
            val imageWidth = drawable.intrinsicWidth * imageHeight / drawable.intrinsicHeight
            imageRect.set(0, 0, imageWidth, imageHeight)
        } else {
            //Use width
            val imageWidth = measuredWidth
            val imageHeight = drawable.intrinsicHeight * imageWidth / drawable.intrinsicWidth
            imageRect.set(0, 0, imageWidth, imageHeight)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bitmap?.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)

        if (drawable == null) return
        if (bitmap == null) bitmap = (drawable as BitmapDrawable).bitmap

        if (width == 0 || height == 0) return
        canvas?.drawBitmap(bitmap, null, imageRect, null)
    }
}