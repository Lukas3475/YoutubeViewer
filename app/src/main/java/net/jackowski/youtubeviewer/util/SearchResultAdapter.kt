package net.jackowski.youtubeviewer.util

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.jackowski.youtubeviewer.MainActivity
import net.jackowski.youtubeviewer.R
import net.jackowski.youtubeviewer.model.SearchResult
import java.net.URL
import java.util.concurrent.Executors

class SearchResultAdapter(
    private val mainActivity: MainActivity,
    private val searchResults: List<SearchResult>
) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        return SearchResultHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_result_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        val searchResult = searchResults[position]
        holder.title.text = searchResult.snippet.title
        holder.author.text = searchResult.snippet.channelTitle
        holder.thumbnail.layoutParams.height = searchResult.snippet.thumbnails.high.height
        holder.thumbnail.layoutParams.width = searchResult.snippet.thumbnails.high.width
        getThumbnails(holder, searchResult)
        holder.bindClickEvent()
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    private fun getThumbnails(holder: SearchResultHolder, searchResult: SearchResult) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val image =
                    BitmapFactory.decodeStream(URL(searchResult.snippet.thumbnails.high.url).openStream())
                Handler(Looper.getMainLooper()).post {
                    holder.thumbnail.setImageBitmap(image)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    class SearchResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.searchResultTitle)
        val author: TextView = itemView.findViewById(R.id.searchResultAuthor)
        val thumbnail: ImageView = itemView.findViewById(R.id.searchResultThumbnail)

        fun bindClickEvent(){
            itemView.setOnClickListener {
                Log.d("Dupa", "Dupaaaaaa")
            }
        }
    }
}