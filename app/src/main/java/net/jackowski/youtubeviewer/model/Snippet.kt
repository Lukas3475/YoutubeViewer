package net.jackowski.youtubeviewer.model

data class Snippet(
    var publishedAt: String = "",
    var title:String = "",
    var description: String = "",
    var thumbnails: List<Thumbnail> = ArrayList(),
    var channelTitle: String = "",
    var defaultLanguage: String = "",
    var localized: Localized = Localized(),
    var defaultAudioLanguage: String = ""
)