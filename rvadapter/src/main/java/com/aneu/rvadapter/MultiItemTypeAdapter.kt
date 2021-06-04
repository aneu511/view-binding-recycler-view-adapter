package com.aneu.rvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.aneu.rvadapter.delegate.ItemViewDelegate
import com.aneu.rvadapter.delegate.ItemViewDelegateManager
import com.aneu.rvadapter.viewholder.ViewHolder

open class MultiItemTypeAdapter<T>(val context: Context, val data: MutableList<T>) :
    RecyclerView.Adapter<ViewHolder<out ViewBinding>>() {

    constructor(context: Context) : this(context, ArrayList<T>())

    private var itemViewDelegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    private var onItemClickListener: ((view: View?, holder: RecyclerView.ViewHolder?, position: Int) -> Unit)? =
        null

    fun setOnItemClickListener(listener: ((view: View?, holder: RecyclerView.ViewHolder?, position: Int) -> Unit)?) {
        onItemClickListener = listener
    }

    private var onItemLongClickListener: ((view: View?, holder: RecyclerView.ViewHolder?, position: Int) -> Boolean)? =
        null

    fun setOnItemLongClickListener(listener: ((view: View?, holder: RecyclerView.ViewHolder?, position: Int) -> Boolean)?) {
        onItemLongClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemViewDelegateManager.itemViewDelegateCount > 0)
            itemViewDelegateManager.getItemViewType(
                data[position],
                position
            )
        else
            super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<out ViewBinding> {
        val itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType)
        val viewBinding = itemViewDelegate!!.inflateItemViewBinding()
            .invoke(LayoutInflater.from(context), parent, false)
        val holder = ViewHolder.createViewHolder(viewBinding)
        setListener(holder)
        return holder
    }

    private fun setListener(viewHolder: ViewHolder<out ViewBinding>) {
        viewHolder.convertView.setOnClickListener { v ->
            onItemClickListener?.invoke(v, viewHolder, viewHolder.adapterPosition)
        }
        viewHolder.convertView.setOnLongClickListener { v ->
            onItemLongClickListener?.invoke(v, viewHolder, viewHolder.adapterPosition)
                ?: false
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<out ViewBinding>, position: Int) {
        itemViewDelegateManager.onBindViewHolder(holder, data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItemViewDelegate(itemViewDelegate: ItemViewDelegate<T, out ViewBinding>): MultiItemTypeAdapter<T> {
        itemViewDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    fun replaceData(data: List<T>) {
        replaceData(data, true)
    }

    fun replaceData(data: List<T>, notify: Boolean) {
        this.data.clear()
        this.data.addAll(data)
        if (notify) {
            notifyDataSetChanged()
        }
    }
}
