package com.aneu.rvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.aneu.rvadapter.delegate.ItemViewDelegate
import com.aneu.rvadapter.viewholder.ViewHolder

abstract class SingleTypeAdapter<T, VB : ViewBinding> : MultiItemTypeAdapter<T> {

    protected abstract fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> VB

    protected abstract fun delegateOnBindViewHolder(holder: ViewHolder<VB>, item: T, position: Int)

    protected open fun delegateOnViewRecycled(holder: ViewHolder<VB>) {}

    constructor(context: Context) : this(context, ArrayList<T>())

    constructor(context: Context, data: ArrayList<T>) : super(context, data) {

        addItemViewDelegate(object : ItemViewDelegate<T, VB> {

            override fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> VB {
                return this@SingleTypeAdapter.inflateItemViewBinding()
            }


            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun onBindViewHolder(holder: ViewHolder<VB>, item: T, position: Int) {
                delegateOnBindViewHolder(holder, item, position)
            }

        })
    }


}
