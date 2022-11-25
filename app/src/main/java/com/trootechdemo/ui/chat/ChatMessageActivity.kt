package com.trootechdemo.ui.chat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trootechdemo.R
import com.trootechdemo.databinding.ActivityChatMessageBinding
import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.ChatConversationData
import com.trootechdemo.model.ConversationData
import com.trootechdemo.restapi.api.ApiCallback
import com.trootechdemo.ui.chat.adtr.CategoryAdapter
import com.trootechdemo.ui.chat.adtr.ChatAdapter
import com.trootechdemo.ui.chat.adtr.ChatMessagesAdapter
import com.trootechdemo.utils.Progress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatMessageActivity : AppCompatActivity() {

    //View model object create
    val mainViewModel by viewModels<ChatViewModel>()
    lateinit var progress: Progress
    lateinit var mContext: Context
    lateinit var binding: ActivityChatMessageBinding
    var from_id: Int = 0
    var to_id: Int = 0
    var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_message)
        mContext = this

        from_id = intent.getIntExtra("from_id", 0)
        to_id = intent.getIntExtra("to_id", 0)
        username = intent.getStringExtra("username")!!

        init()
        setProgressbar()
        callGetChatMessageListApi()
    }

    fun setProgressbar() {
        progress = Progress(this, lifecycle)
        progress.setCancelable(false)
    }

    lateinit var chatAdapter: ChatAdapter
    private fun init() {
        with(binding) {

            val linearLayoutManager = LinearLayoutManager(this@ChatMessageActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            linearLayoutManager.stackFromEnd = true
            linearLayoutManager.isSmoothScrollbarEnabled = false
            linearLayoutManager.reverseLayout = false
            rvChat.layoutManager = linearLayoutManager
            //


//            rvChat.addOnScrollListener(object :
//                RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if (dy > 0) { //check for scroll down
//                        visibleItemCount = linearLayoutManager.childCount
//                        totalItemCount = linearLayoutManager.itemCount
//                        pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
//
//                        if (!isLoading) {
//                            if (!isLastPage) {
//                                if (pastVisiblesItems == 0) {
//                                    currentPage++
//                                    isLoading = true
//                                    callGetAllMessageApi(currentPage, false)
//                                }
//                            }
//                        }
//                    }
//
//                }
//
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                }
//            })


            imgSend.setOnClickListener {
                if (edtSendCommentACM.text.toString().trim().isEmpty()) {
                    Toast.makeText(
                        this@ChatMessageActivity,
                        "Please type something to send",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                callSendMessageApi()

            }

        }
    }

    private fun callSendMessageApi() {
        var message = binding.edtSendCommentACM.text
        progress.show()
        mainViewModel.fetchSendMessageResponseApi(
            api_key_values = "ef38719f441609f6e7b22a9533d9ff580e15e12a0a70db58bcb124ea5cb2689dae266b4341114783118bd558033a1016fcc82560c65cca5f",
            to_userid = to_id,
            message = message.toString()
        )
        /**
         * Using Observer through response handle..
         * **/
        mainViewModel.responseSendMessageResponse.observe(this) { response ->
            when (response) {
                is ApiCallback.OnSuccess -> {
                    progress.hide()
                    binding.edtSendCommentACM.setText("")

                var chatdata=    ChatConversationData(
                    1669116911,
                    response.data?.data!!.from,
                    "",
                    response.data?.data!!.from_delete,
                    username,
                    response.data?.data!!.id,
                    response.data?.data!!.media,
                    response.data?.data!!.message_type,
                    response.data?.data!!.seen,
                    response.data?.data!!.sticker,
                    response.data?.data!!.text,
                    response.data?.data!!.to,
                    "",
                    response.data?.data!!.to_delete,
                    "",
                    response.data?.data!!.message_type
                    )

                    alChatUserList.add(alChatUserList.size -1,chatdata)
                    chatAdapter.notifyDataSetChanged()

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

    lateinit var alChatUserList: ArrayList<ChatConversationData>
    var arrayList = ArrayList<ChatConversationData>()
    var currentPage: Int = 0

    private fun callGetChatMessageListApi() {
        progress.show()
        mainViewModel.fetchCallGetChatConversationList(
            limit = 100,
            api_key_values = "ef38719f441609f6e7b22a9533d9ff580e15e12a0a70db58bcb124ea5cb2689dae266b4341114783118bd558033a1016fcc82560c65cca5f",
            to_userid = to_id,
            is_unread = 0
        )
        /**
         * Using Observer through response handle..
         * **/
        mainViewModel.responseGetChatConversation.observe(this) { response ->
            when (response) {
                is ApiCallback.OnSuccess -> {
                    progress.hide()

                    if (currentPage == 0) {
                        alChatUserList = ArrayList()
                    }

                    arrayList = ArrayList()

                    alChatUserList.addAll(response.data!!.data)

                    bindData()

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


    private fun bindData() {
        try {

            val manager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            binding.rvChat!!.setLayoutManager(manager)

            chatAdapter = ChatAdapter(alChatUserList, to_id, mContext!!)
            binding.rvChat!!.adapter = chatAdapter
            chatAdapter!!.notifyItemInserted(alChatUserList.size - 1)

            Handler().postDelayed({
                binding.rvChat!!.scrollToPosition(alChatUserList.size - 1)
            }, 100)


            //scrollToBottom()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}