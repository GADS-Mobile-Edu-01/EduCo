package com.android.educo.views.main.ui.catalogue.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.educo.R
import com.android.educo.model.TextDocument
import com.android.educo.views.auth.SignUpActivity
import com.google.android.material.snackbar.Snackbar

class DocRecyclerAdapter(
    private val context: Context,
    private val DocList:List<TextDocument>):
    RecyclerView.Adapter<DocRecyclerAdapter.viewHolder>() {


    val layoutInflater = LayoutInflater.from(context)

    inner class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById(R.id.txtDoctitle) as TextView
        val description = itemView.findViewById(R.id.txtDocDesc) as TextView
        val courseDuration = itemView.findViewById(R.id.duration) as TextView
        var TextDocPosition = -1

        // Initialize onClick Event on the ViewHolder Card
        init {
            itemView.setOnClickListener {
//                TODO("Navigate to the appropriate fragment")
                Toast.makeText(context, "$TextDocPosition is the course id", Toast.LENGTH_LONG).show()
                Log.d("DOCTEXT", "$TextDocPosition is the course id")
//                Intent(context, SignUpActivity::class.java).apply {
//                    putExtra(TEXTDOC_POSITION, TextDocPosition)
//                }.also { context.startActivity(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): viewHolder {
        val itemView = layoutInflater
            .inflate(R.layout.catalogue_doc_text_card, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val document = DocList[position]
        bindHolder(holder, document)
        holder.TextDocPosition = position
    }

    override fun getItemCount() = DocList.size

    companion object{
        val TEXTDOC_POSITION = "TEXTDOC_POSITION"

        // this function sets value to the textViews of the ViewHolder card
        // it takes in the viewHolder and returns nothing
        private fun bindHolder(holder: viewHolder,
                       document: TextDocument){
            holder.title.text = document.title
            holder.description.text = document.courseDescription
            holder.courseDuration.text = document.duration

        }
    }

}