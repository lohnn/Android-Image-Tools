package se.lohnn.imagecropperdemo

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import se.lohnn.imageviewer.ImageViewer
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by lohnn on 09/03/16.
 * © Infomaker Scandinavia AB
 */

class MainActivity : AppCompatActivity() {
    companion object {
        private var CROP_REQUEST = 1

        /**
         * Reads an image from asset folder
         *
         * @param assetManager
         * @param fileName
         * @return
         */
        private fun getBitmapFromAsset(assetManager: AssetManager?, fileName: String): Bitmap? {
            try {
                val instr = assetManager?.open(fileName)
                val bitmap = BitmapFactory.decodeStream(instr)
                return bitmap
            } catch(e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * Saves a Bitmap to file
         *
         * @param imageToSave
         * @param saveLocation
         * @return
         */
        private fun saveImageToFile(imageToSave: Bitmap?, saveLocation: File): Boolean {
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(saveLocation)
                imageToSave?.compress(Bitmap.CompressFormat.JPEG, 100, out)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (out != null) {
                        out.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        openImageView(this)
    }

    fun openImageView(context: Context) {
        val cropperIntent = Intent(context, ImageViewer::class.java)
        val tempImageFileName = "testImage.jpg"
        val tempImageFile = File(cacheDir, tempImageFileName)

        //If demo app has not been run before
        //(temp image in cache folder does not exist)
        if (!tempImageFile.exists()) {
            //Load image from asset
            val tempImage = getBitmapFromAsset(assets, tempImageFileName)
            //Save image to cache folder
            tempImageFile.parentFile.mkdirs()
            saveImageToFile(tempImage, tempImageFile)
        }

        cropperIntent.putExtra(ImageViewer.IMAGE_PATH, tempImageFile.absolutePath)
        startActivityForResult(cropperIntent, CROP_REQUEST)
    }
}