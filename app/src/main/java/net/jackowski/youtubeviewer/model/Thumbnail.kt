package net.jackowski.youtubeviewer.model

data class Thumbnail(
    var medium: Medium = Medium(),
    var high: High = High()
)

data class Medium(
    var url: String = "",
    var width: Int = 0,
    var height: Int = 0
)

data class High(
    var url: String = "",
    var width: Int = 0,
    var height: Int = 0
)