package com.example.notes.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.NoteEntity
import kotlinx.android.synthetic.main.notes_item.view.*

class NotesAdapter(
    private val noteEntityList: List<NoteEntity>,
    private val onRecyclerItemClicked: OnRecyclerItemClicked
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notes_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return noteEntityList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.title.text = noteEntityList[position].title
        holder.detail.text = noteEntityList[position].detail
        holder.bid(noteEntityList[position].id)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_title
        val detail: TextView = itemView.tv_note_detail

        fun bid(id:Int){
            itemView.setOnClickListener {
                onRecyclerItemClicked.onNoteClicked(id)
            }
        }
    }

}