package com.trootechdemo.ui.main.adtr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trootechdemo.R
import com.trootechdemo.databinding.RowTopViewBinding
import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.TopViewModel

class TopViewAdapter(
    var mContext: Context,
    var alTopList: ArrayList<TopViewModel>,
    var btnClicked: RecyclerViewClickListners
) : RecyclerView.Adapter<TopViewAdapter.TopViewViewHolder>() {

    companion object {
        var mClickedListners: RecyclerViewClickListners? = null
    }

    class TopViewViewHolder(val binding: RowTopViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): TopViewViewHolder = TopViewViewHolder(
                RowTopViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        fun bind(item: TopViewModel, mContext: Context) {
            binding.tvTopViewTitle.text=item.title
            binding.ivTopView.setImageResource(item.icon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewViewHolder =
        TopViewViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: TopViewViewHolder, position: Int) {
        holder.bind(alTopList[position], mContext)
        mClickedListners = btnClicked


        if (alTopList[position].isSelect){
            holder.binding.rlMain.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.item_select_round_bg))
        }
        holder.binding.rlMain.setOnClickListener {
            if (mClickedListners != null) {
                mClickedListners?.onClick(position)
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int = alTopList.size

}