package com.android.educo.views.main.ui.offline.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android.educo.databinding.OfflineMaterialRecyclerViewItemBinding
import com.android.educo.offline.Offline
import com.android.educo.views.details.DetailsActivity

/**
 * Author: A. L. Zailani
 */
class OfflineMaterialsAdapter() :
    RecyclerView.Adapter<OfflineMaterialsAdapter.OfflineViewHolder>() {

    private var materials = emptyList<Offline>()

    inner class OfflineViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(offline: Offline) {
            val binding = (binding as OfflineMaterialRecyclerViewItemBinding)
            binding.model = offline

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailsActivity::class.java)
                intent.putExtra("is_offline", true)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineViewHolder {
        val binding = OfflineMaterialRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        binding.root.layoutParams = RecyclerView.LayoutParams(
            parent.layoutParams.width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return OfflineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfflineViewHolder, position: Int) {
        holder.bind(materials[position])
    }

    override fun getItemCount(): Int = materials.size

    fun materials(materials: List<Offline>) {
        this.materials = materials

        notifyDataSetChanged()
    }
}