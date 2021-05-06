package com.example.reportcity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.reportcity.api.ServiceBuilder
import com.example.reportcity.api.endpoints.reports
import com.example.reportcity.api.entities.allReports
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment(), GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest


    //Resolver permissão
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        if (ActivityCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION  ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),  1111)
            return@OnMapReadyCallback
        }
        mMap.setInfoWindowAdapter(infoWindow(requireContext()))
        googleMap.setOnInfoWindowClickListener(this)

        mMap.isMyLocationEnabled = true
        mMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE)

        /*
            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        */

        val request = ServiceBuilder.buildService(reports::class.java)
        val call = request.getAllReports()

        call.enqueue(object  : Callback<List<allReports>> {
            override fun onResponse(call: Call<List<allReports>>, response: Response<List<allReports>>) {

                val loginShared: SharedPreferences? = activity?.getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
                val idUser = loginShared?.getInt(getString(R.string.idLogin), 0)
                val userLogin = loginShared?.getString(getString(R.string.userLogin), "")

                if(response.isSuccessful) {
                    for(item in response.body()!!) {
                        val loca = LatLng(item.reports.lat,item.reports.lng)

                        if(item.users.id == idUser ) {
                            googleMap.addMarker(MarkerOptions().position(loca).title(item.reports.name).icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_AZURE
                                    )
                            ).snippet(item.reports.description))
                        }else{
                            googleMap.addMarker(MarkerOptions().position(loca).title(item.reports.name).icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                            BitmapDescriptorFactory.HUE_ORANGE
                                    )
                            ).snippet(item.reports.description))
                        }

                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.69470468361408, -8.846846503887253), 9.0f))

                }else{

                }
            }

            override fun onFailure(call: Call<List<allReports>>, t: Throwable) {
                Log.d("REPORTS TESTS", t.message.toString())
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_LONG).show()
            }
        })



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        if( ActivityCompat.checkSelfPermission(view.context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),  1111)
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                if (p0 != null) {
                    lastLocation = p0.lastLocation
                }
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                //mMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE)
            }
        }


        createLocationRequest()




    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION  ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),  1111)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d(" PAUSE REQUEST", "App em pausa")
    }

    override fun onInfoWindowClick(p0: com.google.android.gms.maps.model.Marker?) {
        Toast.makeText(requireContext(), "INFO INFO", Toast.LENGTH_LONG).show()
    }
/*
    override  fun onBackPressed() {
        Toast.makeText(requireContext(), "nao volta", Toast.LENGTH_SHORT).show()
    }
*/
}

