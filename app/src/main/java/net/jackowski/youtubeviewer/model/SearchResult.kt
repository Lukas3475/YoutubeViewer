package net.jackowski.youtubeviewer.model

data class SearchResult(
    var snippet: Snippet = Snippet(),
    var contentDetails: ContentDetails = ContentDetails(),
    var statistics: Statistics = Statistics(),
    var player: Player
)