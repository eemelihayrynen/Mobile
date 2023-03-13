package com.example.mobile

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.*
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity9 : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("moro: ", "moro")
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()
    }

    private fun fetchLocation() {
        Log.d("moro: ", "moro")
        val task = fusedLocationProviderClient.lastLocation
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        if(checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
            return}
        task.addOnSuccessListener {
            if(it != null){
                Log.d("long: ", it.longitude.toString())
                Log.d("lat: ", it.latitude.toString())
                val a = it.longitude.toString()
                val b = it.latitude.toString()
                editor.putString("latitude", b)
                editor.putString("longitude", a)
                editor.apply()
            }
        }
    }


}