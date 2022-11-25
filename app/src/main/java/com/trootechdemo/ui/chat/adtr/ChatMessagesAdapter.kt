package com.trootechdemo.ui.chat.adtr

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.trootechdemo.R
import com.trootechdemo.databinding.RowChatMsgBubbleBinding
import com.trootechdemo.model.ChatConversationData

class ChatMessagesAdapter(
    var context: Context,
    var arrayList: ArrayList<ChatConversationData>,
    var to_id:Int
) :
    RecyclerView.Adapter<ChatMessagesAdapter.ChatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_chat_msg_bubble, parent, false
            )
        )

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(arrayList[position], position)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    var profilePhoto: String = ""
    var userName: String = ""

    fun setData(arrayList: ArrayList<ChatConversationData>, profile_photo: String, username: String) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList)
        profilePhoto = profile_photo
        userName = username
        notifyDataSetChanged()
    }

    fun addNewMessage(data: ChatConversationData, profile_photo: String, username: String) {
        profilePhoto = profile_photo
        userName = username
        arrayList.add(data)
        notifyItemInserted(arrayList.size - 1)
    }


    var mediaController: MediaController? = null
    //  lateinit var alPollOption: Option

    inner class ChatHolder(val mBinding: RowChatMsgBubbleBinding) : RecyclerView.ViewHolder(mBinding.root) {


        fun bind(item: ChatConversationData, position: Int) {
            with(mBinding)
            {

                // mClickListener = btnlistener

                try {
                    tvRecNameRCMB.text = userName

//                    if (profilePhoto != null && profilePhoto != "")
//                        Glide.with(context)
//                            .load(profilePhoto)
//                            .into(ivProfileRCMB)
//                    else
//                        ivProfileRCMB.setImageDrawable(context.resources.getDrawable(R.drawable.ic_place_holder))

//                    ivProfileRCMB.shapeAppearanceModel =
//                        ivProfileRCMB.shapeAppearanceModel
//                            .toBuilder()
//                            .setTopRightCorner(CornerFamily.ROUNDED, 0f)
//                            .setTopLeftCorner(
//                                CornerFamily.ROUNDED,
//                                context.resources.getDimension(R.dimen._10sdp)
//                            )
//                            .setBottomLeftCorner(
//                                CornerFamily.ROUNDED,
//                                context.resources.getDimension(R.dimen._10sdp)
//                            )
//                            .setBottomRightCorner(
//                                CornerFamily.ROUNDED,
//                                context.resources.getDimension(R.dimen._10sdp)
//                            )
//                            .build()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                txtMsgMe.text = item.text
                txtMsgOther.text = item.text

                if (item.from ==to_id) {
                    llMsgMe.visibility = View.VISIBLE

                    txtMsgMe.text = item.text

                    txtTimeOther.visibility = View.GONE
                    llMsgOther.visibility = View.GONE

                    llMsgMeText.visibility = View.VISIBLE
                } else {
                    llMsgOther.visibility = View.VISIBLE
                    llMsgMe.visibility = View.GONE
                    txtTimeOther.visibility = View.VISIBLE


                    txtMsgOther.text = item.text

                    rlRecMsgMainView.visibility = View.VISIBLE
                    llMsgOtherText.visibility = View.VISIBLE
                }




            }
        }
    }


}