package com.trootechdemo.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.trootechdemo.R
import com.trootechdemo.databinding.ActivityMainBinding
import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.BottomViewModel
import com.trootechdemo.model.TopViewModel
import com.trootechdemo.ui.main.adtr.BottomViewAdapter
import com.trootechdemo.ui.main.adtr.TopViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this

        isFirstTimeCall()
        setListData()
    }

    //Todo : Application first time initialization time view managed runtime with scale data.
    private fun isFirstTimeCall() {
        with(binding) {
            rlHeader.rlMain.visibility = View.VISIBLE
            rlHeader.rlMain.post {
                rcVwBottomView.animate().translationY(rlHeader.rlMain.height.toFloat())
            }
        }
    }

    private fun setListData() {
        setTopView()
        setBottomView()
        binding.rlHeader.ivMenu.setOnClickListener {
            setAnimaitonData()
        }
    }

    /*
    * Top View and Bottom view managed with header. Animation scale maneged as per Top view hight.
    * */
    var isOpenClosed: Boolean = true
    private fun setAnimaitonData() {

        //Access animation scale data
        var scaleOpen: Animation = AnimationUtils.loadAnimation(mContext, R.anim.left_to_right)
        var scaleClosed: Animation = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left)

        //visibility managed with animated view
        if (isOpenClosed) {
            isOpenClosed = false
            with(binding) {
                rlHeader.rlMain.visibility = View.GONE
                rcVwTopView.visibility = View.VISIBLE

                /*Using post through scale height set for top of view.
                 means if top view visible with scale so bottom view down with top of side.*/
                rcVwTopView.post {
                    rcVwBottomView.animate().translationY(rcVwTopView.height.toFloat())
                }
                rcVwTopView.startAnimation(scaleOpen)

            }
        } else {
            isOpenClosed = true
            with(binding) {
                rlHeader.rlMain.visibility = View.VISIBLE
                /*Using post through scale height set for Bottom of view.
                means top view gone so bottom view transaction scale 0 required. as per graph X and Y using.*/
                rcVwTopView.post {
                    rcVwBottomView.animate().translationY(0f)
                }
                rlHeader.rlMain.post {
                    rcVwBottomView.animate().translationY(rlHeader.rlMain.height.toFloat())
                }
                rcVwTopView.startAnimation(scaleClosed)
                rcVwTopView.visibility = View.GONE
            }
        }
    }


    /*
    * Static data set in ArrayList
    * */
    lateinit var alBottomList: ArrayList<BottomViewModel>
    private fun setBottomViewList() {
        alBottomList = ArrayList()

        var topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)

        topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)

        topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)

        topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)


        topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)

        topData = BottomViewModel(
            0,
            mContext.resources.getString(R.string.title_values),
            mContext.resources.getString(R.string.des_values),
            false
        )
        alBottomList.add(topData)


    }

    /*
    * Bottom view set in adapter
    * */
    private fun setBottomView() {
        setBottomViewList()
        var adapter = BottomViewAdapter(
            mContext, alBottomList, object : RecyclerViewClickListners {
                override fun onClick(position: Int) {

                }

            })
        binding.rcVwBottomView.adapter = adapter
        binding.rcVwBottomView.layoutManager = LinearLayoutManager(this)
    }


    //Set Top main View
    lateinit var adapter: TopViewAdapter
    private fun setTopView() {
        setTopViewList()

        adapter = TopViewAdapter(this, alTopList, object : RecyclerViewClickListners {
            override fun onClick(position: Int) {
                alTopList[position].isSelect != alTopList[position].isSelect
                setAnimaitonData()
                adapter.notifyDataSetChanged()
            }

        })
        val layoutManager = GridLayoutManager(this, 3)
        binding.rcVwTopView.layoutManager = layoutManager
        binding.rcVwTopView.adapter = adapter
    }

    //Set Top main View List static data
    lateinit var alTopList: ArrayList<TopViewModel>
    private fun setTopViewList() {
        alTopList = ArrayList()

        var topData = TopViewModel(0, "Inbox", R.drawable.ic_inbox, true)
        alTopList.add(topData)

        topData = TopViewModel(1, "Starred", R.drawable.ic_star, false)
        alTopList.add(topData)

        topData = TopViewModel(2, "Snoozed", R.drawable.ic_clock, false)
        alTopList.add(topData)

        topData = TopViewModel(3, "Important", R.drawable.ic_mail_schedule, false)
        alTopList.add(topData)

        topData = TopViewModel(4, "Sent", R.drawable.ic_send, false)
        alTopList.add(topData)

        topData = TopViewModel(5, "Scheduled", R.drawable.ic_mail_schedule, false)
        alTopList.add(topData)

        topData = TopViewModel(6, "Draft", R.drawable.ic_draft, false)
        alTopList.add(topData)

        topData = TopViewModel(7, "All mail", R.drawable.ic_mail_schedule, false)
        alTopList.add(topData)

        topData = TopViewModel(8, "Spam", R.drawable.ic_spam, false)
        alTopList.add(topData)
    }
}