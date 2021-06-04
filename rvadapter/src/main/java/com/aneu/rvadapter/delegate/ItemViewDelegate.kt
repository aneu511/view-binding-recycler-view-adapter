package com.aneu.rvadapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.aneu.rvadapter.viewholder.ViewHolder

interface ItemViewDelegate<T, VB : ViewBinding> {

    fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> VB

    fun isForViewType(item: T, position: Int): Boolean

    fun onBindViewHolder(holder: ViewHolder<VB>, item: T, position: Int)

}
