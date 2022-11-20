package net.jackowski.youtubeviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.jackowski.youtubeviewer.model.SearchResult
import net.jackowski.youtubeviewer.util.SearchResultAdapter

class MainActivity : AppCompatActivity() {
    lateinit var searchResults: List<SearchResult>
    lateinit var gSon: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSearches()
        setContentView(R.layout.activity_main)
        val view = findViewById<RecyclerView>(R.id.searchResultsView)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = SearchResultAdapter(this, searchResults)
    }


    private fun loadSearches() {
        gSon = Gson()
        searchResults = gSon.fromJson(
            applicationContext.resources.openRawResource(
                applicationContext.resources.getIdentifier(
                    "mobiles_api",
                    "raw",
                    application.packageName
                )
            ).bufferedReader().use { it.readText() },
            object : TypeToken<List<SearchResult>>() {}.type
        )
    }
}