package com.example.mobile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobile.model.RemindDAO
import com.example.mobile.model.lista
import com.example.mobile.model.nimi
import com.example.mobile.model.room.viewModelProviderFactoryOf
import com.example.mobile.repository.RemindRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import java.lang.reflect.Array.set
import java.util.*

@Composable
fun ReminderLocationMap(    navController: NavController){
    val mapView = rememberMapViewWithLifecycle()
    val courutineScope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 36.dp)
    ) {
        AndroidView({mapView}) {
            mapView ->
            courutineScope.launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true
                val location = LatLng (65.06, 25.47)

                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(location, 15f)
                )
                val markerOptions = MarkerOptions()
                    .title("Welcome to Oulu")
                    .position(location)
                map.addMarker(markerOptions)

                setMapLongClick(map = map,navController)
            }
        }
    }
}

private fun setMapLongClick(
    map: GoogleMap,
    navController: NavController
) {
    map.setOnMapClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )
        Log.d("sijainnit",latlng.toString())

        map.addMarker(
            MarkerOptions().position(latlng).title("Virtual location").snippet(snippet)
        )
        val d = latlng.toString().split(",")
        val e = funktioe(d)
        val f = funktiof(d)
        for (i in 1 until  lista.size){
            val a = lista[i].split(" ")[0].toDouble()
            val b = lista[i].split(" ")[1].toDouble()
            Log.d("loop i",i.toString())
            val c = LatLng(a,b)
            if (a-e<0.01&&a-e>0.00 && b-f<0.01&&b-f>0.00 || a-e>-0.01 && a-e<0.00 && b-f>-0.01 && b-f<0.00 || a-e>-0.01 && a-e<0.00 &&b-f<0.01&&b-f>0.00 ||b-f>-0.01 && b-f<0.00 && a-e<0.01&&a-e>0.00 ){
                map.addMarker(
                    MarkerOptions().position(c).title(nimi[i]).snippet(snippet)
                )}
        }
        Log.d("sijainnit",lista.toString())
        Log.d("sijainnit",nimi.toString())
    }
    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        map.addMarker(
            MarkerOptions().position(latlng).title("New reminder location").snippet(snippet)
        ).apply {

            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("location_data", latlng)
        }
    }}
fun funktioe(d : List<String>):Double{
    try{
        val e = d[0].takeLast(17).toDouble()
        return e
    }
    catch (e: java.lang.NumberFormatException){
        try{
            val e = d[0].takeLast(16).toDouble()
            return e}catch (e: java.lang.NumberFormatException){
            val e = d[0].takeLast(15).toDouble()
            return e
        }
    }
}
fun funktiof(d : List<String>):Double{
    try{
        val f = d[1].take(17).toDouble()
        return f
    }
    catch (e: java.lang.NumberFormatException){
        try{
        val f = d[1].take(16).toDouble()
        return f}catch (e: java.lang.NumberFormatException){
            val f = d[1].take(15).toDouble()
            return f
        }
    }
}
