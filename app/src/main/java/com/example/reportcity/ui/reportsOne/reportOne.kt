package com.example.reportcity.ui.reportsOne

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.example.reportcity.R
import com.example.reportcity.api.ServiceBuilder
import com.example.reportcity.api.entities.allReports
import com.example.reportcity.api.entities.reports
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class reportOne : Fragment() {

    companion object {
        fun newInstance() = reportOne()
    }

    private lateinit var viewModel: ReportOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.report_one_fragment, container, false)

        val idReport: Int? = arguments?.getInt("idReport", 0)

        val name: TextView = root.findViewById(R.id.nameReportOne)
        val image: ImageView = root.findViewById(R.id.imageReportOne)
        val description: TextView = root.findViewById(R.id.descriptionReportOne)
        val coord: TextView = root.findViewById(R.id.coordReportOne)
        val morada: TextView = root.findViewById(R.id.moradaReportOne)

        val request = ServiceBuilder.buildService(com.example.reportcity.api.endpoints.reports::class.java)
        val call = request.getReport(idReport!!)

        call.enqueue(object  : Callback<reports> {
            override fun onResponse(call: Call<reports>, response: Response<reports>) {
                if(response.isSuccessful) {
                    name.text = response.body()!!.name
                    //image.text =response.body()!!.name
                    description.text = response.body()!!.description
                    coord.text = "LAT: " + response.body()!!.lat.toString() + "LNG: " + response.body()!!.lng.toString()
                    morada.text = response.body()!!.morada
                }else{

                }
            }

            override fun onFailure(call: Call<reports>, t: Throwable) {
                Log.d("REPORTS TESTS", t.message.toString())
            }
        })

        return root
    }

}