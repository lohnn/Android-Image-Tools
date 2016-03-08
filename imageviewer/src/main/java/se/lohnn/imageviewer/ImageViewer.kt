package se.lohnn.imageviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import se.lohnn.imageviewer.image.ImageLoader
import se.lohnn.imageviewer.views.ImageViewerView

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
class ImageViewer() : AppCompatActivity() {
    companion object {
        val IMAGE_PATH = "ImagePath"
    }

    private var imageViewer: ImageViewerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val imageLocation = intent.getStringExtra(IMAGE_PATH)

        imageViewer = ImageViewerView(this)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val image = ImageLoader.loadImage(imageLocation, metrics.widthPixels, metrics.heightPixels)
        imageViewer?.setImageBitmap(image)

        val toolbar = supportActionBar
        toolbar?.title = "Hejsan"
        toolbar?.hide()

        setContentView(imageViewer)

        /*
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
         */
        /*
         Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
        */
    }
}