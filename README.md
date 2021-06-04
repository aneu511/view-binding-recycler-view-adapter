# ViewBindingRecyclerViewAdapter
ViewBinding with RecyclerViewAdapter

## 启用View Binding 功能
在module的build.gradle文件加上
```
android {
    ...
    buildFeatures{
        viewBinding = true
    }
}
```

## 使用
每种Item类型对应一个ItemViewDelegate
```
val adapter = MultiItemTypeAdapter(context, data)
            .addItemViewDelegate(object : ItemViewDelegate<XXObject, ABinding> {
                override fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> ABinding {
                    return ABinding::inflate
                }

                override fun isForViewType(item: XXObject, position: Int): Boolean {
                    return if (you need ABinding) true else false
                }

                override fun onBindViewHolder(
                    holder: ViewHolder<ABinding>,
                    item: String,
                    position: Int
                ) {
                    holder.viewBinding.tvXX.text = item
                }

            }).addItemViewDelegate(object : ItemViewDelegate<XXObject, BBinding> {
                override fun inflateItemViewBinding(): (LayoutInflater, ViewGroup, Boolean) -> BBinding {
                    return BBinding::inflate
                }

                override fun isForViewType(item: XXObject, position: Int): Boolean {
                    return if (you need BBinding) true else false
                }

                override fun onBindViewHolder(
                    holder: ViewHolder<BBinding>,
                    item: String,
                    position: Int
                ) {
                    holder.viewBinding.tvItemLetter.text = item
                }


            })
```

## 参考
https://github.com/hongyangAndroid/baseAdapter
