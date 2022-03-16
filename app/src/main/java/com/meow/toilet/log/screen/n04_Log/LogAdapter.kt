package com.meow.toilet.log.screen.n04_Log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.meow.toilet.log.databinding.LogListItemBinding

/**
 * 04_ログ画面 アダプター.
 */
class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    // region 変数

    /** ビューホルダー */
    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    /** データリスト */
    var itemList: List<LogItemData> = arrayListOf()

    // endregion 変数

    // region 継承

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LogListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding as LogListItemBinding
        binding.data = itemList[position]
    }

    override fun getItemCount() = itemList.size

    // endregion 継承

    // region 公開

    /**
     * アダプター更新処理.
     * @param itemList データリスト
     */
    fun update(itemList: List<LogItemData>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    // endregion 公開


}