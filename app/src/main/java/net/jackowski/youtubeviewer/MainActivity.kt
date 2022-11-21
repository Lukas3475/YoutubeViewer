package net.jackowski.youtubeviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.jackowski.youtubeviewer.model.SearchResult
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var searchResults: List<SearchResult>
    lateinit var gSon: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSearches()
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.mainLayout, SearchResultsFragment(searchResults)).commit()
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
        searchResults = searchResults.filter {
            !it.contentDetails.regionRestriction.blocked.contains(Locale.getDefault().country) || it.contentDetails.regionRestriction.allowed.contains(
                Locale.getDefault().country
            )
        }
    }
}