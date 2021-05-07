package com.example.reportcity.ui.reportsAll

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.R
import com.example.reportcity.api.ServiceBuilder
import com.example.reportcity.api.entities.allReports
import com.example.reportcity.api.entities.reports
import com.example.reportcity.drawerNav
import com.example.reportcity.ui.reports.reportsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportsAllFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.fragment_reports, container, false)

    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerviewReportsAll)

    val adapter = reportsAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext())

    val request = ServiceBuilder.buildService(com.example.reportcity.api.endpoints.reports::class.java)
    val call = request.getAllReports()
    val allReportsLiveData = MutableLiveData<List<reports?>>()

    call.enqueue(object  : Callback<List<allReports>> {
      override fun onResponse(call: Call<List<allReports>>, response: Response<List<allReports>>) {

        if(response.isSuccessful) {
          var arrAllReports: Array<reports?> = arrayOfNulls<reports>(response.body()!!.size)

          for ((index, item) in response.body()!!.withIndex()) {
            arrAllReports[index] = item.reports
          }

          var allReports: List<reports?> = arrAllReports.asList()

          allReportsLiveData.value = allReports


          allReportsLiveData.observe(requireActivity()) { reports ->
            reports.let { adapter.submitList(it) }
          }

        }else{

        }
      }

      override fun onFailure(call: Call<List<allReports>>, t: Throwable) {
        Log.d("REPORTS TESTS", t.message.toString())
      }
    })

    adapter.setOnItemClick(object : reportsAdapter.onItemClick {
      override fun onViewClick(position: Int) {
        Log.d("REPORTS ALL", "CLICAR CLICAR")
        val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0

        (activity as drawerNav).navController.navigate(R.id.nav_one_report, bundleOf("idReport"  to id))
      }

    })




    return root
  }
}