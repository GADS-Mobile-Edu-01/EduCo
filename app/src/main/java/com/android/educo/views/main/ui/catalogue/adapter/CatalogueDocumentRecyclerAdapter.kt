package com.android.educo.views.main.ui.catalogue.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.educo.R
import com.android.educo.model.Catalogue
import kotlinx.android.synthetic.main.catalogue_doc_text_card.view.*


/*
Created by
Oshodin Osemwingie

on 28/10/2020.
*/
class CatalogueDocsRecyclerAdapter(val context : Context, val clickListener: CatalogueDocsClickListener) : ListAdapter<Catalogue, CatalogueDocsRecyclerAdapter.MyViewHolder>(
    CatalogueDocsDiffCallback()
) {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            item: Catalogue,
            clickListener: CatalogueDocsClickListener
        ) {
            itemView.setOnClickListener {
                clickListener.onClick(item)
            }
            itemView.txtDoctitle.text = item.title
            itemView.txtDocDesc.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }
    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.catalogue_doc_text_card,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context,item,clickListener)
    }
}

class CatalogueDocsClickListener(val clickListener: (item : Catalogue) -> Unit){
    fun onClick(item: Catalogue) = clickListener(item)
}

class CatalogueDocsDiffCallback : DiffUtil.ItemCallback<Catalogue>(){
    override fun areItemsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean {
        return oldItem == newItem
    }
}

