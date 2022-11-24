package net.jackowski.youtubeviewer.util

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import net.jackowski.youtubeviewer.R
import net.jackowski.youtubeviewer.model.SearchResult
import org.joda.time.DateTime
import java.net.URL
import java.util.concurrent.Executors

class SearchResultDetailsFragment(
    private val searchResults: List<SearchResult>,
    private val searchResult: SearchResult
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_result_details, container, false)
        view.findViewById<Button>(R.id.searchResultDetailsBtn).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainLayout, SearchResultsFragment(searchResults)).commit()
        }
        setSeeMoreInfoButton(view, container!!)
        setFields(view)
        return view
    }

    private fun setSeeMoreInfoButton(view: View, container: ViewGroup) {
        val searchResultDetailsMoreInfo =
            view.findViewById<ConstraintLayout>(R.id.searchResultDetailsMoreInfo)
        val btn = view.findViewById<ImageButton>(R.id.searchResultDetailsShowMoreBtn)
        searchResultDetailsMoreInfo.visibility = View.GONE
        btn.setOnClickListener {
            if (searchResultDetailsMoreInfo.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(container, AutoTransition())
                searchResultDetailsMoreInfo.visibility = View.GONE
                btn.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(container, AutoTransition())
                searchResultDetailsMoreInfo.visibility = View.VISIBLE
                btn.setImageResource((R.drawable.ic_baseline_expand_less_24))
            }
        }
    }

    private fun setFields(view: View) {
        view.findViewById<TextView>(R.id.searchResultDetailsTitle).text = searchResult.snippet.title
        getThumbnail(view.findViewById(R.id.searchResultDetailsThumbnail), searchResult)
        view.findViewById<TextView>(R.id.searchResultDetailsAuthor).text =
            searchResult.snippet.channelTitle
        view.findViewById<TextView>(R.id.searchResultDetailsDescription).text =
            searchResult.snippet.description
        view.findViewById<TextView>(R.id.searchResultDetailsPublishedAt).text =
            DateTime(searchResult.snippet.publishedAt).toString("HH:mm dd-mm-yyyy ")
        view.findViewById<TextView>(R.id.searchResultDetailsViewCount).text =
            searchResult.statistics.viewCount
        view.findViewById<TextView>(R.id.searchResultDetailsLikeCount).text =
            searchResult.statistics.likeCount
        view.findViewById<TextView>(R.id.searchResultDetailsFavoriteCount).text =
            searchResult.statistics.favoriteCount
        view.findViewById<TextView>(R.id.searchResultDetailsCommentCount).text =
            searchResult.statistics.commentCount
        view.findViewById<VideoView>(R.id.searchResultDetailsVideoPlayer)
            .setVideoPath(searchResult.player.embedHtml)
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