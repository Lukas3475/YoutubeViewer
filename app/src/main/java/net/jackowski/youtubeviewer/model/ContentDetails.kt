package net.jackowski.youtubeviewer.model

data class ContentDetails(
    var duration: String = "",
    var dimension: String = "",
    var definition: String = "",
    var licensedContent: Boolean = false,
    var regionRestriction: RegionRestriction = RegionRestriction()
)