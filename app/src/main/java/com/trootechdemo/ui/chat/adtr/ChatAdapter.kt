package com.trootechdemo.ui.chat.adtr


import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trootechdemo.R
import com.trootechdemo.model.ChatConversationData
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    var chatHistory: List<ChatConversationData>,
    var to_id: Int,
    var context: Context,
) : RecyclerView.Adapter<ChatAdapter.MyViewHolder?>(), Handler.Callback {

    lateinit var SIMPLE_TIME_FORMAT: SimpleDateFormat
    lateinit var SIMPLE_DATE_FORMAT: SimpleDateFormat
    lateinit var calendar: Calendar
    var time: String? = null
    var strDate = ""
    var path: String? = null
    var playingPosition: Int = 0

    lateinit var uiUpdateHandler: Handler
    var from = ""
    private var old_role_flag = 1

    var lastSelectedPosition: Int = -1

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, i: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item_message, parent, false)
        return MyViewHolder(view)
    }

    var myClip: ClipData? = null
    var myClipboard: ClipboardManager? = null

    override fun onBindViewHolder(@NonNull myViewHolder: MyViewHolder, pos: Int) {


        try {
            myClipboard = context!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            time = chatHistory.get(pos).created_at.toString()
            val timestamp = time!!.toLong() * 1000L

/*
* Check here To_Id same as login user id so show view in right side or show view left side
* */

            if (chatHistory[pos].from != to_id) {
                old_role_flag = 1

                //sender
                (myViewHolder.chat_sent_item.findViewById(R.id.tvSendName) as TextView).visibility =
                    View.GONE


                if (!chatHistory[pos].text.equals("") && chatHistory.get(pos).text != null) {
                    myViewHolder.chat_sent_item.setVisibility(View.VISIBLE)
                    myViewHolder.chat_receive_item.setVisibility(View.GONE)

                    (myViewHolder.chat_sent_item.findViewById(R.id.text_message_body_sent) as TextView).setText(
                        chatHistory[pos].text
                    )
                    (myViewHolder.chat_sent_item.findViewById(R.id.text_message_time_sent) as TextView).text =
                        getDate(timestamp)

                    (myViewHolder.chat_sent_item.findViewById(R.id.text_message_body_sent) as TextView).setOnClickListener {
                        val text: String =
                            (myViewHolder.chat_sent_item.findViewById(R.id.text_message_body_sent) as TextView).text.toString()
                        myClip = ClipData.newPlainText("text", text)
                        myClipboard!!.setPrimaryClip(myClip!!)
                        Toast.makeText(
                            context, "Text Copied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


            } else {

                //receiver
                (myViewHolder.chat_receive_item.findViewById(R.id.tvRecName) as TextView).visibility =
                    View.GONE

                if (!chatHistory[pos].text.equals("") && chatHistory[pos].text != null
                ) {
                    (myViewHolder.chat_receive_item.findViewById(R.id.text_message_body) as TextView).setText(
                        chatHistory[pos].text
                    )
                    myViewHolder.chat_sent_item.setVisibility(View.GONE)
                    myViewHolder.chat_receive_item.setVisibility(View.VISIBLE)
                    (myViewHolder.chat_receive_item.findViewById(R.id.text_message_time) as TextView).text =
                        getDate(timestamp)

                    (myViewHolder.chat_receive_item.findViewById(R.id.text_message_body) as TextView).setOnClickListener {
                        val text: String =
                            (myViewHolder.chat_sent_item.findViewById(R.id.text_message_body_sent) as TextView).text.toString()
                        myClip = ClipData.newPlainText("text", text)
                        myClipboard!!.setPrimaryClip(myClip!!)
                        Toast.makeText(
                            context, "Text Copied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        } catch (e: Exception) {
            myViewHolder.chat_sent_item.setVisibility(View.GONE)
            myViewHolder.chat_receive_item.setVisibility(View.GONE)
            e.printStackTrace()
        }

//        try {
//            val getDate: String = chatHistory.get(pos).getTime()
//            if (getDate != "" && getDate != null) {
//                myViewHolder.txt_tstamp_receive.visibility = View.VISIBLE
//                myViewHolder.viewDate.visibility = View.GONE
//                val time: Long = chatHistory.get(pos).getTime().toLong() * 1000L
//                var previousTs: Long = 0
//                if (pos > 1) {
//                    val pm: ChatMessageNode = chatHistory[pos - 1]
//                    previousTs = pm.getTime().toLong() * 1000L
//                }
//
//                setTimeTextVisibility(time, previousTs, myViewHolder.txt_tstamp_receive, pos,myViewHolder.viewDate)
//
//            } else {
//                myViewHolder.txt_tstamp_receive.visibility = View.GONE
//                myViewHolder.viewDate.visibility = View.GONE
//            }
//        } catch (e: Exception) {
//            myViewHolder.txt_tstamp_receive.visibility = View.GONE
//            myViewHolder.viewDate.visibility = View.GONE
//            e.printStackTrace()
//        }

    }



    /*
    * If required time so managed here
    * */
    var isFirstTimeShowDate = false
    private fun setTimeTextVisibility(
        time: Long,
        previousTs: Long,
        date_try: TextView,
        position: Int,
        viewDate: View
    ) {
        val sdf = SimpleDateFormat("MMM d, yyyy")
        // SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
        val resultdate = Date(time)

        if (previousTs == 0L) {
            date_try.visibility = View.VISIBLE
            date_try.text = sdf.format(resultdate)
            if (position == 0) {
                date_try.visibility = View.VISIBLE
                viewDate.visibility = View.GONE
                date_try.text = sdf.format(resultdate)
            } else {
                date_try.visibility = View.GONE
                viewDate.visibility = View.GONE
                date_try.text = ""
            }
        } else {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.timeInMillis = time
            cal2.timeInMillis = previousTs

            val sameMonth =
                cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.MONTH] == cal2[Calendar.MONTH] && cal1[Calendar.DATE] == cal2[Calendar.DATE]
            if (sameMonth) {
                date_try.visibility = View.GONE
                viewDate.visibility = View.GONE
                date_try.text = ""
            } else {
                viewDate.visibility = View.GONE
                date_try.visibility = View.VISIBLE
                date_try.text = sdf.format(resultdate)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return chatHistory!!.size
    }

    /*  val itemCount: Int
          get() = chatHistory.size*/

    var dialog: Dialog? = null


    private fun getDate(timeStamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("hh:mm a")
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (ex: Exception) {
            "xx"
        }
    }

    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            MSG_UPDATE_SEEK_BAR -> {
                try {
                    uiUpdateHandler.sendEmptyMessageDelayed(
                        MSG_UPDATE_SEEK_BAR,
                        100
                    )
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return false
    }

    inner class MyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var chat_sent_item: RelativeLayout
        lateinit var chat_receive_item: RelativeLayout
        lateinit var txt_tstamp_receive: TextView
        lateinit var viewDate: View
        lateinit var imgVwSentChatImage: ImageView
        lateinit var imgVwSentChatPdf: ImageView

        lateinit var imgVwProfilePicRec: ImageView
        var imgVwProfilePicSender: ImageView


        init {
            // ll_sent_message = itemView.findViewById(R.id.ll_sent_message)
            // listener = mListener
            imgVwProfilePicRec = itemView.findViewById(R.id.imgVwProfilePicRec)
            imgVwProfilePicSender = itemView.findViewById(R.id.imgVwProfilePicSender)

            chat_sent_item = itemView.findViewById(R.id.chat_item_sent)
            chat_receive_item = itemView.findViewById(R.id.chat_item_receive)
            txt_tstamp_receive = itemView.findViewById(R.id.txt_tstamp_receive)
            viewDate = itemView.findViewById(R.id.viewDate)
            imgVwSentChatImage = itemView.findViewById(R.id.imgVwSentChatImage)
        }
    }

    companion object {
        //for audio :  media player
        private const val MSG_UPDATE_SEEK_BAR = 1845
        var mediaPlayer: MediaPlayer? = null
        var lastSelectedPosition = -1
    }

    init {
        SIMPLE_TIME_FORMAT = SimpleDateFormat("dd-MMM-yy  HH:mm")
        SIMPLE_DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")
        calendar = Calendar.getInstance()
        this.chatHistory = chatHistory
        this.from = from
        this.context = context
        // this.mListener = mListener
        playingPosition = -1
        uiUpdateHandler = Handler(this)
    }


}