package com.example.reportcity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class infoWindow(context: Context) : GoogleMap.InfoWindowAdapter {


    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.activity_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val name = view.findViewById<TextView>(R.id.infoNameReport)
        val description = view.findViewById<TextView>(R.id.infoDescriptionReport)
        val image = view.findViewById<ImageView>(R.id.infoImageReport)
        image.setImageResource(R.drawable.user)

        name.text = marker.title
        description.text = marker.snippet

        /* Picasso.get().load(strs[1]).into(image);       //Passar o link da imagem

         image.getLayoutParams().height = 450;
         image.getLayoutParams().width = 450;
         image.requestLayout();
*/
    }


    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}