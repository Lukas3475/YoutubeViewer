package net.jackowski.youtubeviewer.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.jackowski.youtubeviewer.MainActivity
import net.jackowski.youtubeviewer.R
import net.jackowski.youtubeviewer.model.SearchResult

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
        CacheManager.getThumbnail(holder.thumbnail, mainActivity, searchResult)
        holder.bindClickEvent(mainActivity, searchResults, searchResult)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    class SearchResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.searchResultTitle)
        val author: TextView = itemView.findViewById(R.id.searchResultAuthor)
        val thumbnail: ImageView = itemView.findViewById(R.id.searchResultThumbnail)

        fun bindClickEvent(
            mainActivity: MainActivity,
            searchResults: List<SearchResult>,
            searchResult: SearchResult
        ) {
            itemView.setOnClickListener {
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.mainLayout,
                        SearchResultDetailsFragment(mainActivity, searchResults, searchResult)
                    ).commit()
            }
        }
    }
}