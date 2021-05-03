package com.plete.mygooglemapsapps

import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val jkt = LatLng(-6.2088, 106.8456)
        val bdg = LatLng(-6.9175, 107.6191)
        val sby = LatLng(-7.2575, 112.7521)
        val jbr = LatLng(-8.1733, 113.7009)
//        val home

        val jktLoc = Location("")
        jktLoc.latitude = -6.2088
        jktLoc.longitude = 106.8456

        val sbyLoc = Location("")
        sbyLoc.latitude = -7.2575
        sbyLoc.longitude = 112.7521

        val bdgLoc = Location("")
        bdgLoc.latitude = -6.9175
        bdgLoc.longitude = 107.6191

        val jbrLoc = Location("")
        jbrLoc.latitude = -8.1733
        jbrLoc.longitude = 113.7009


        val jktTOsby = jktLoc.distanceTo(sbyLoc)
        val bdgTOjbr = bdgLoc.distanceTo(jbrLoc)
        val sbyTObdg = sbyLoc.distanceTo(bdgLoc)
        val jbrTOjkt = jbrLoc.distanceTo(jktLoc)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(bdg))
        mMap.addMarker(MarkerOptions().position(jkt).title("Jarak JKT SBY $jktTOsby")).showInfoWindow()
        mMap.addMarker(MarkerOptions().position(bdg).title("Jarak BDG SBY $bdgTOjbr")).showInfoWindow()
        mMap.addMarker(MarkerOptions().position(sby).title("Jarak SBY BDG $sbyTObdg")).showInfoWindow()
        mMap.addMarker(MarkerOptions().position(jbr).title("Jarak JBR JKT $jbrTOjkt")).showInfoWindow()

        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(jkt, sby)
                .color(R.color.teal_200)
                .width(15f))

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(bdg, jbr)
                .color(R.color.teal_200)
                .width(15f))

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(sby, bdg)
                .color(R.color.teal_200)
                .width(15f))

        mMap.addPolyline(PolylineOptions()
                .clickable(true)
                .add(jbr, jkt)
                .color(R.color.teal_200)
                .width(15f))

        mMap.addCircle(CircleOptions()
                .center(jbr)
                .clickable(true)
                .radius(10000.0)
                .strokeColor(R.color.white))

        mMap.setOnCameraIdleListener {
            var currentLocation: LatLng = mMap.cameraPosition.target
            val geocoder = Geocoder(this)
            mMap.clear()
            geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)
            mMap.addMarker(MarkerOptions().position(currentLocation).title(""))
        }

        mMap.isTrafficEnabled=true

//        mMap.uiSettings.isMapToolbarEnabled =true
    }
}