package com.aneu.example.rvadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aneu.example.rvadapter.databinding.ActivityExampleBinding
import com.aneu.example.rvadapter.databinding.ItemExampleLetterBinding
import com.aneu.example.rvadapter.databinding.ItemExampleNumBinding
import com.aneu.rvadapter.MultiItemTypeAdapter
import com.aneu.rvadapter.delegate.ItemViewDelegate
import com.aneu.rvadapter.viewholder.ViewHolder

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.rvExample.layoutManager = LinearLayoutManager(this)
        val data = arrayListOf("a", "b", "1", "c", "2", "3", "d", "e", "f", "g", "h", "4", "i", "5", "j", "6", "k", "8")
        val adapter = MultiItemTypeAdapter(this, data)
            .addItemViewDelegate(object : ItemViewDelegate<String, ItemExampleNumBinding> {
                override fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> ItemExampleNumBinding {
                    return ItemExampleNumBinding::inflate
                }

                override fun isForViewType(item: String, position: Int): Boolean {
                    return item.matches(Regex("^[0-9]*$"))
                }

                override fun onBindViewHolder(
                    holder: ViewHolder<ItemExampleNumBinding>,
                    item: String,
                    position: Int
                ) {
                    holder.viewBinding.tvItemNum.text = item
                }

            }).addItemViewDelegate(object : ItemViewDelegate<String, ItemExampleLetterBinding> {
                override fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> ItemExampleLetterBinding {
                    return ItemExampleLetterBinding::inflate
                }

                override fun isForViewType(item: String, position: Int): Boolean {
                    return !item.matches(Regex("^[0-9]*$"))
                }

                override fun onBindViewHolder(
                    holder: ViewHolder<ItemExampleLetterBinding>,
                    item: String,
                    position: Int
                ) {
                    holder.viewBinding.tvItemLetter.text = item
                }


            })

        adapter.setOnItemClickListener { view, holder, position ->
            Toast.makeText(this@ExampleActivity, data[position], Toast.LENGTH_SHORT).show()
        }

        viewBinding.rvExample.adapter = adapter
    }
}