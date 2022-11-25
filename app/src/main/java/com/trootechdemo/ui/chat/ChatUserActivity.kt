package com.trootechdemo.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.trootechdemo.R
import com.trootechdemo.databinding.ActivityChatUserBinding
import com.trootechdemo.restapi.api.ApiCallback
import dagger.hilt.android.AndroidEntryPoint
import androidx.databinding.DataBindingUtil
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.ConversationData
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.ui.chat.ChatViewModel
import com.trootechdemo.ui.chat.adtr.CategoryAdapter
import com.trootechdemo.utils.ConnectivityDetector
import com.trootechdemo.utils.Progress

@AndroidEntryPoint
class ChatUserActivity : AppCompatActivity() {
    //View model object create
    val mainViewModel by viewModels<ChatViewModel>()
    lateinit var progress: Progress
    lateinit var binding: ActivityChatUserBinding
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_user)
        mContext = this


        init()
        setProgressbar()


        if (ConnectivityDetector.isConnectingToInternet(mContext)) {
            mainViewModel.deleteAllNotes()
            callGetChatUserListApi()
        } else
            callGetOfflineUserList()
    }

    fun setProgressbar() {
        progress = Progress(this, lifecycle)
        progress.setCancelable(false)
    }

    private fun init() {
        with(binding) {

        }
    }

    lateinit var alChatUserList: ArrayList<ConversationData>
    private fun callGetChatUserListApi() {
        progress.show()
        mainViewModel.fetchCategoryListResponse(
            limit = 100,
            offset = 1,
            api_token_values = "ef38719f441609f6e7b22a9533d9ff580e15e12a0a70db58bcb124ea5cb2689dae266b4341114783118bd558033a1016fcc82560c65cca5f"
        )
        /**
         * Using Observer through response handle..
         * **/
        mainViewModel.responseGetCategoryMain.observe(this) { response ->
            when (response) {
                is ApiCallback.OnSuccess -> {
                    progress.hide()
                    alChatUserList = ArrayList()
                    alChatUserList.addAll(response.data?.data!!)
                    setCategoryView()

                    //Store in local databasek
                    mainViewModel.insert(response.data)

                }

                is ApiCallback.OnError -> {
                    progress.hide()
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ApiCallback.OnLoading -> {
                    progress.hide()
                }
            }
        }
    }

    private fun setCategoryView() {
        var adapterPeople = CategoryAdapter(mContext, alChatUserList, object :
            RecyclerViewClickListners {
            override fun onClick(position: Int) {
                var intent = Intent(mContext, ChatMessageActivity::class.java)
                intent.putExtra("from_id", alChatUserList[position].from_id)
                intent.putExtra("to_id", alChatUserList[position].to_id)
                intent.putExtra("username", alChatUserList[position].user!!.first_name)
                startActivity(intent)
            }
        })
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rcVwChatUserList.layoutManager = linearLayoutManager
        adapterPeople.also { binding.rcVwChatUserList.adapter = it }
        adapterPeople.notifyDataSetChanged()
    }


    fun callGetOfflineUserList() {
        alChatUserList = ArrayList()

        mainViewModel.getAllNotes().observe(this, Observer {
            var alData: List<ConversationListResponse> = it
            var newData: ArrayList<ConversationData> = alData[0].data!!
            alChatUserList = ArrayList()
            alChatUserList.addAll(newData)
            Log.e("Get  datta--=-=--> ", "${alData}")
            setCategoryView()
        })

    }
}