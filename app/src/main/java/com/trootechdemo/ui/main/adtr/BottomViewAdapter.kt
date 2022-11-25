package com.trootechdemo.ui.main.adtr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trootechdemo.databinding.RowBottomViewBinding

import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.BottomViewModel

class BottomViewAdapter(
    var mContext: Context,
    var alTopList: ArrayList<BottomViewModel>,
    var btnClicked: RecyclerViewClickListners
) : RecyclerView.Adapter<BottomViewAdapter.TopViewViewHolder>() {

    companion object {
        var mClickedListners: RecyclerViewClickListners? = null
    }

    class TopViewViewHolder(val binding: RowBottomViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): TopViewViewHolder = TopViewViewHolder(
                RowBottomViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        fun bind(item: BottomViewModel, mContext: Context) {
            binding.tvTopViewTitle.text=item.titleBottom
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewViewHolder =
        TopViewViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: TopViewViewHolder, position: Int) {
        holder.bind(alTopList[position], mContext)
        mClickedListners = btnClicked

        holder.binding.rlMain.setOnClickListener {
            if (mClickedListners != null) {
                mClickedListners?.onClick(position)
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int = alTopList.size

}