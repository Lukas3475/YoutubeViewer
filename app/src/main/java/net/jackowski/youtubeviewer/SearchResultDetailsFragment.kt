package net.jackowski.youtubeviewer

import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import net.jackowski.youtubeviewer.model.SearchResult
import net.jackowski.youtubeviewer.util.SearchResultAdapter
import java.net.URL
import java.util.concurrent.Executors

class SearchResultDetailsFragment(private val searchResults: List<SearchResult>, private val searchResult: SearchResult) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_result_details, container, false)
        view.findViewById<Button>(R.id.searchResultDetailsBtn).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainLayout, SearchResultsFragment(searchResults)).commit()
        }
        setFields(view)
        return view
    }


    private fun setFields(view: View){
        view.findViewById<TextView>(R.id.searchResultDetailsTitle).text = searchResult.snippet.title
        getThumbnail(view.findViewById(R.id.searchResultDetailsThumbnail), searchResult)
        view.findViewById<TextView>(R.id.searchResultDetailsAuthor).text = searchResult.snippet.channelTitle
    }

    private fun getThumbnail(imageView: ImageView, searchResult: SearchResult) {
        imageView.layoutParams.height = searchResult.snippet.thumbnails.high.height
        imageView.layoutParams.width = searchResult.snippet.thumbnails.high.width
        Executors.newSingleThreadExecutor().execute {
            try {
                val image =
                    BitmapFactory.decodeStream(URL(searchResult.snippet.thumbnails.high.url).openStream())
                Handler(Looper.getMainLooper()).post {
                    imageView.setImageBitmap(image)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}