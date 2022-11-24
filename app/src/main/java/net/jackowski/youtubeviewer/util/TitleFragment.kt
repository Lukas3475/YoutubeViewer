package net.jackowski.youtubeviewer.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import net.jackowski.youtubeviewer.R
import net.jackowski.youtubeviewer.model.SearchResult

class TitleFragment(private val searchResults: List<SearchResult>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title, container, false)
        view.findViewById<Button>(R.id.StartAppBtn).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainLayout, SearchResultsFragment(searchResults)).commit()
        }
        return view
    }

}