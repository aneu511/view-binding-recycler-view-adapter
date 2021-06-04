package com.aneu.rvadapter.delegate

import androidx.collection.SparseArrayCompat
import androidx.viewbinding.ViewBinding
import com.aneu.rvadapter.viewholder.ViewHolder

class ItemViewDelegateManager<T> {
    private var delegates: SparseArrayCompat<ItemViewDelegateProxy<T, out ViewBinding>> =
        SparseArrayCompat()
    val itemViewDelegateCount: Int
        get() = delegates.size()

    fun addDelegate(delegate: ItemViewDelegate<T, out ViewBinding>): ItemViewDelegateManager<T> {
        delegates.put(delegates.size(), ItemViewDelegateProxy(delegate))
        return this
    }


    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)
            if (delegate!!.isForViewType(item, position)) {
                return delegates.keyAt(i)
            }
        }
        throw IllegalArgumentException(
            "No ItemViewDelegate added that matches position=$position in data source"
        )
    }

    fun onBindViewHolder(holder: ViewHolder<out ViewBinding>, item: T, position: Int) {
        val delegate = getItemViewDelegateProxy(holder.itemViewType)
            ?: throw java.lang.NullPointerException(
                ("No delegate found for item at position = "
                        + position
                        + " for viewType = "
                        + holder.itemViewType)
            )
        delegate.toBindViewHolder(holder, item, position)
    }

    private fun getItemViewDelegateProxy(viewType: Int): ItemViewDelegateProxy<T, out ViewBinding>? {
        return delegates[viewType]
    }

    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<T, out ViewBinding>? {
        return getItemViewDelegateProxy(viewType)
    }

    private inner class ItemViewDelegateProxy<T, VB : ViewBinding>(itemViewDelegate: ItemViewDelegate<T, VB>) :
        ItemViewDelegate<T, VB> by itemViewDelegate {

        @Suppress("UNCHECKED_CAST")
        fun toBindViewHolder(holder: ViewHolder<out ViewBinding>, item: T, position: Int) {
            onBindViewHolder(holder as ViewHolder<VB>, item, position)
        }
    }
}
