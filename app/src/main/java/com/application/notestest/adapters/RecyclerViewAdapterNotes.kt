package com.application.notestest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.application.notestest.dataclasses.DataNotes
import com.application.notestest.R
import com.application.notestest.interfaces.OnItemClickListenerNotes

class RecyclerViewAdapterNotes(
    private val listDataNotes: List<DataNotes>,
    private val itemClickListenerNotes: OnItemClickListenerNotes
    ) :
    RecyclerView.Adapter<RecyclerViewAdapterNotes.ViewHolder>() {
    private lateinit var contextAdapter: Context

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun getItemCount(): Int {
        return listDataNotes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contextAdapter = parent.context
        val cv = LayoutInflater.from(contextAdapter)
            .inflate(R.layout.card_notes, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView

        val title: TextView = cardView.findViewById(R.id.text_view_title)
        val description: TextView = cardView.findViewById(R.id.text_view_description)
        val dateTime: TextView = cardView.findViewById(R.id.text_view_date)
        val favorite: ImageView = cardView.findViewById(R.id.iv_favorite)

        title.text = listDataNotes[position].title
        description.text = listDataNotes[position].description
        dateTime.text = listDataNotes[position].date
        if (listDataNotes[position].favorite){
            favorite.visibility = View.VISIBLE
            favorite.setImageResource(R.drawable.icon_favorite_40)
        }else{
            favorite.visibility = View.GONE
        }


        cardView.setOnClickListener {
            itemClickListenerNotes.onItemClickNotes(position)
        }
    }

}
