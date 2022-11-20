package net.jackowski.youtubeviewer.model

data class Snippet(
    var publishedAt: String = "",
    var title:String = "",
    var description: String = "",
    var thumbnails: Thumbnail = Thumbnail(),
    var channelTitle: String = "",
    var defaultLanguage: String = "",
    var localized: Localized = Localized(),
    var defaultAudioLanguage: String = ""
)