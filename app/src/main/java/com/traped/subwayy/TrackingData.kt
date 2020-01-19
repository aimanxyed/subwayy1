package com.traped.subwayy

import com.google.android.gms.maps.model.LatLng

object TrackingData {
    var locationCallback: (latLng: LatLng) -> Unit = {}
}