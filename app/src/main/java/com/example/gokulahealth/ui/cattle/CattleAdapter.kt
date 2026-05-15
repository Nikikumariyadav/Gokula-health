package com.example.gokulahealth.ui.cattle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokulahealth.R
import com.example.gokulahealth.data.Cattle
import com.example.gokulahealth.databinding.ItemCattleBinding

class CattleAdapter(
    private val onItemClick: (Cattle) -> Unit,
    private val onDeleteClick: (Cattle) -> Unit
) : ListAdapter<Cattle, CattleAdapter.VH>(DIFF) {

    inner class VH(val b: ItemCattleBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCattleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cattle = getItem(position)
        with(holder.b) {
            tvCattleName.text = cattle.name
            tvEarTag.text = "🏷️ ${cattle.earTag}"
            tvBreed.text = cattle.breed
            Glide.with(ivCattlePhoto.context)
                .load(if (cattle.photoUri.isNotBlank()) cattle.photoUri else R.drawable.ic_cow_placeholder)
                .placeholder(R.drawable.ic_cow_placeholder)
                .circleCrop()
                .into(ivCattlePhoto)
            root.setOnClickListener { onItemClick(cattle) }
            btnDelete.setOnClickListener { onDeleteClick(cattle) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Cattle>() {
            override fun areItemsTheSame(a: Cattle, b: Cattle) = a.id == b.id
            override fun areContentsTheSame(a: Cattle, b: Cattle) = a == b
        }
    }
}
