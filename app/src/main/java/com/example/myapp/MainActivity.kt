package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.UserListAdapter
import com.example.myapp.api.factory.UsersViewModelFactory
import com.example.myapp.api.listener.UsersApiRequest
import com.example.myapp.api.repository.UsersRepository
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.interfaces.OnItemClickListener
import com.example.myapp.model.UserData
import com.example.myapp.model.UserList
import com.example.myapp.utils.addItemDecorationWithoutLastItem
import com.example.myapp.utils.dialog
import com.example.myapp.utils.getData
import com.example.myapp.utils.hideLoader
import com.example.myapp.utils.isNetworkAvailable
import com.example.myapp.utils.isShowApiMessage
import com.example.myapp.utils.request
import com.example.myapp.utils.setEmptyViewWithStaticMessage
import com.example.myapp.utils.showLoader
import com.example.myapp.utils.toast
import com.example.myapp.viewmodel.DatabaseViewModel
import com.example.myapp.viewmodel.DatabaseViewModelFactory
import com.example.myapp.viewmodel.UsersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var userListAdapter:UserListAdapter?=null
    var arrayUsers= arrayListOf<com.example.myapp.room.UserData>()
    private var intPage: Int = 1
    private lateinit var apiRequest: UsersApiRequest
    private lateinit var viewModel: UsersViewModel
    private lateinit var databaseViewModel:DatabaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setAdapter()
        setObserver()

    }

    private fun init(){

        databaseViewModel = ViewModelProvider(this@MainActivity, DatabaseViewModelFactory(this@MainActivity))[DatabaseViewModel::class.java]
        apiRequest=request(UsersApiRequest::class.java)
        viewModel = ViewModelProvider(this@MainActivity,
            UsersViewModelFactory(UsersRepository(apiRequest)))[UsersViewModel::class.java]

        apiCallForUsers()
    }


    private fun setAdapter() {
        userListAdapter = UserListAdapter(this, arrayUsers, object : OnItemClickListener {
            override fun onItemClick(value: Any?) {
                super.onItemClick(value)
            }
        })

        binding.apply {
            rvUserList.addItemDecorationWithoutLastItem()
            rvUserList.adapter = userListAdapter

            rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lastCompletelyVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    if (intPage > 0 && lastCompletelyVisibleItemPosition + 1 == arrayUsers.size && dialog?.isShowing == false) {
                        apiCallForUsers()
                    }
                }
            })
        }
    }

    private fun apiCallForUsers() {
        if (isNetworkAvailable(this@MainActivity)) {
            showLoader()

            viewModel.apiCallForUsers(intPage)

        } else {
            toast(getString(R.string.err_no_internet))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserver() {
        viewModel.getUsersApiResponse.observe(this@MainActivity) { apiResponse ->
            hideLoader()

            isShowApiMessage(apiResponse, this@MainActivity)

            apiResponse?.normalData?.let { data ->
                val userdata = getData(data, UserData::class.java)

               userdata?.data?.let { userList->
                   userList.forEach { userdataApi->
                       val userdata= com.example.myapp.room.UserData()
                       userdata.id = userdataApi.id?.toDouble()?.toInt()
                       userdata.first_name=userdataApi.first_name
                       userdata.last_name=userdataApi.last_name
                       userdata.email=userdataApi.email
                       userdata.avatar=userdataApi.avatar
                       databaseViewModel.insertUser(userdata)
                   }
               }
            }
        }

        databaseViewModel.getUser()?.observe(this) { arrayList->
            if (arrayList.isNotEmpty()) {
                arrayUsers.addAll(arrayList)
                intPage++
            }
            userListAdapter?.notifyDataSetChanged()
            setEmptyViewWithStaticMessage(arrayUsers.isEmpty(), binding.rvUserList, binding.responseNotFound.llEmpty, binding.responseNotFound.tvEmptyView,getString(R.string.no_data_found))
        }

    }

}

