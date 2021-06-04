package com.aneu.rvadapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewHolder<VB : ViewBinding>(val viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {

    val convertView: View
        get() = viewBinding.root

    companion object {
        fun <VB : ViewBinding> createViewHolder(viewBinding: VB): ViewHolder<VB> {
            return ViewHolder(viewBinding)
        }
    }

}
