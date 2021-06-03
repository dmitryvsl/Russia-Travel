package com.example.russiatravel.network.model

data class Route(
    val bounds: Bounds,
    val copyrights: String,
    val legs: List<Any>,
    val overview_polyline: OverviewPolyline,
    val summary: String,
    val warnings: List<Any>,
    val waypoint_order: List<Any>
)