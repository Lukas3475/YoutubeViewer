package net.jackowski.youtubeviewer.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import net.jackowski.youtubeviewer.MainActivity
import net.jackowski.youtubeviewer.R
import net.jackowski.youtubeviewer.model.SearchResult
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object CacheManager {

    fun getThumbnail(imageView: ImageView, mainActivity: MainActivity, searchResult: SearchResult) {
        var image: Bitmap?
        Thread {
            if (!checkIfConnectedToInternet(mainActivity.applicationContext)) {
                val fileName =
                    "${mainActivity.cacheDir}/${searchResult.snippet.publishedAt}-${searchResult.snippet.title}.png"
                image = BitmapFactory.decodeStream(FileInputStream(File(fileName)))
                mainActivity.runOnUiThread { imageView.setImageBitmap(image) }
            } else {
                val url = URL(searchResult.snippet.thumbnails.high.url)
                if ((url.openConnection() as HttpURLConnection).responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    mainActivity.runOnUiThread { imageView.setImageResource(R.drawable.placeholder) }
                } else {
                    image = BitmapFactory.decodeStream(url.openStream())
                    mainActivity.runOnUiThread { imageView.setImageBitmap(image) }
                }
            }
        }.start()
    }

    fun download(mainActivity: MainActivity, searchResults: List<SearchResult>) {
        if (checkIfConnectedToInternet(mainActivity.applicationContext)) {
            Thread {
                searchResults.map { it.snippet }.forEach {
                    val fileName = "${mainActivity.cacheDir}/${it.publishedAt}-${it.title}.png"
                    try {
                        val image =
                            BitmapFactory.decodeStream(URL(it.thumbnails.high.url).openStream())
                        val outputStream = FileOutputStream(File(fileName))
                        image.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }.start()
        }
    }

    private fun checkIfConnectedToInternet(applicationContext: Context): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork ?: return false
        ) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}