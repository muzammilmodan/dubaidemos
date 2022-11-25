package com.trootechdemo.ui.chat.adtr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.trootechdemo.databinding.RowChatUserListBinding
import com.trootechdemo.listners.RecyclerViewClickListners
import com.trootechdemo.model.ConversationData

class CategoryAdapter(
    var mContext: Context,
    var alLanguage: ArrayList<ConversationData>,
    var btnListener: RecyclerViewClickListners
) : RecyclerView.Adapter<CategoryAdapter.LanguageViewHolder>() {

    companion object {
        var mClickListener: RecyclerViewClickListners? = null
    }

    class LanguageViewHolder(val binding: RowChatUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup): LanguageViewHolder = LanguageViewHolder(
                RowChatUserListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        fun bind(item: ConversationData, mContext: Context) {
            binding.tvUserName.text = item.user!!.fullname
            binding.tvUserLastMsg.text = item.text
            binding.ivProfile.load(item.user!!.avater)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder =
        LanguageViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(alLanguage[position], mContext)

        mClickListener = btnListener

//Main view item clicked listners,,,
        holder.binding.cnstMain.setOnClickListener {
            if (mClickListener != null) {
                mClickListener?.onClick(position)
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int = alLanguage.size

}