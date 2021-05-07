package com.example.reportcity.ui.reports

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.*
import com.example.reportcity.api.ServiceBuilder
import com.example.reportcity.api.entities.allReports
import com.example.reportcity.api.entities.reports
import com.example.reportcity.api.entities.result
import com.example.reportcity.entities.Notas
import com.example.reportcity.ui.reportsOne.reportOne
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportsFragment : Fragment() {

  private lateinit var reportsViewModel: ReportsViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    reportsViewModel =
            ViewModelProvider(this).get(ReportsViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_my_reports, container, false)
   /* //val textView: TextView = root.findViewById(R.id.text_home)
    reportsViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })*/


    val loginShared: SharedPreferences? = activity?.getSharedPreferences(getString(R.string.sharedLogin), Context.MODE_PRIVATE)
    val idUser = loginShared?.getInt(getString(R.string.idLogin), 0)
    val userLogin = loginShared?.getString(getString(R.string.userLogin), "")


    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerviewReports)

    val adapter = reportsAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext())

    val request = ServiceBuilder.buildService(com.example.reportcity.api.endpoints.reports::class.java)
    val call = request.getReportUser(idUser!!)
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


    val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.adapterPosition
        val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0

        //dinamico ID ID
        val callDelete = request.deleteReport(id)

        callDelete?.enqueue(object  : Callback<result> {
          override fun onResponse(call: Call<result>, response: Response<result>) {

                if(response.isSuccessful) {

                  allReportsLiveData.value = allReportsLiveData.value!!.toMutableList().apply {
                    removeAt(position)
                  }.toList()


                  Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_LONG).show()
                }else{
                  Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_LONG).show()
                }
          }

          override fun onFailure(call: Call<result>, t: Throwable) {
                Log.d("REPORTS TESTS", t.message.toString())
          }
        })
      }
    }

    val swipeHandlerEdit = object : SwipeToEditCallback(requireContext()) {
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.adapterPosition
        val id: Int = allReportsLiveData.value?.get(position)?.id ?: 0
        val reportTemp: reports? = allReportsLiveData.value!![position]

        val intent = Intent(requireContext(), addReport::class.java)
        intent.putExtra("id", reportTemp!!.id)
        intent.putExtra("name", reportTemp!!.name)
        intent.putExtra("description", reportTemp!!.description)
        intent.putExtra("lat", reportTemp!!.lat)
        intent.putExtra("lng", reportTemp!!.lng)
        intent.putExtra("morada", reportTemp!!.morada)
        intent.putExtra("image", reportTemp!!.image)
        intent.putExtra("users_id", reportTemp!!.users_id)
        startActivity(intent)
      }
    }

    val itemTouchHelperEdit = ItemTouchHelper(swipeHandlerEdit)
    itemTouchHelperEdit.attachToRecyclerView(recyclerView)

    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(recyclerView)



    return root
  }
}