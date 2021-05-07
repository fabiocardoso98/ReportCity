package com.example.reportcity

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso


class infoWindow(context: Context) : GoogleMap.InfoWindowAdapter {


    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.activity_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val name = view.findViewById<TextView>(R.id.infoNameReport)
        val description = view.findViewById<TextView>(R.id.infoDescriptionReport)
        val image = view.findViewById<ImageView>(R.id.infoImageReport)
        val imageUri = "https://i.imgur.com/tGbaZCY.jpg"


        name.text = marker.title
        description.text = marker.snippet.split("+")[0]
        Picasso.with(view.context).load(marker.snippet.split("+")[2]).into(image)

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