package net.jackowski.youtubeviewer.model

data class RegionRestriction(
    var blocked: Set<String> = HashSet(),
    var allowed: Set<String> = HashSet()
)