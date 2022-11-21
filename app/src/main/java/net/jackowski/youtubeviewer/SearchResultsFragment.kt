package net.jackowski.youtubeviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.jackowski.youtubeviewer.model.SearchResult
import net.jackowski.youtubeviewer.util.SearchResultAdapter

class SearchResultsFragment(private val searchResults: List<SearchResult>) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_search_results, container, false)
        val recyclerView = fragmentView.findViewById<RecyclerView>(R.id.searchResultsView)
        val mainActivity = activity as MainActivity
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = SearchResultAdapter(mainActivity, searchResults)

        return fragmentView
    }

}