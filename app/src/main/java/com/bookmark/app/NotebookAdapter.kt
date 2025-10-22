package com.bookmark.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NotebookAdapter(
    private val onNotebookClick: (Book) -> Unit
) : ListAdapter<BookWithNotes, NotebookAdapter.NotebookViewHolder>(NotebookDiffCallback()) {

    class NotebookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.ivBookCover)
        val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        val noteCount: TextView = itemView.findViewById(R.id.tvNoteCount)
        val lastModified: TextView = itemView.findViewById(R.id.tvLastModified)
        val writingStreak: CircularStreakView = itemView.findViewById(R.id.writingStreakView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notebook, parent, false)
        return NotebookViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        val bookWithNotes = getItem(position)
        val book = bookWithNotes.book
        val notes = bookWithNotes.notes
        
        holder.bookTitle.text = book.title
        holder.noteCount.text = "${notes.size} notes"
        
        // Load book cover
        if (book.coverUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(book.coverUrl)
                .placeholder(R.color.surface_light)
                .into(holder.bookCover)
        } else {
            holder.bookCover.setImageResource(R.color.surface_light)
        }
        
        // Show last modified time
        if (notes.isNotEmpty()) {
            val lastNote = notes.maxByOrNull { it.dateModified }
            if (lastNote != null) {
                holder.lastModified.text = "Last modified: ${getTimeAgo(lastNote.dateModified)}"
            }
        } else {
            holder.lastModified.text = "No notes yet"
        }
        
        // Set writing streak (placeholder - will be calculated)
        holder.writingStreak.setStreakData("D0", 0f, R.color.streak_green)
        
        holder.itemView.setOnClickListener { onNotebookClick(book) }
    }
    
    private fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        return when {
            diff < 60_000 -> "just now"
            diff < 3600_000 -> "${diff / 60_000} minutes ago"
            diff < 86400_000 -> "${diff / 3600_000} hours ago"
            diff < 604800_000 -> "${diff / 86400_000} days ago"
            else -> SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(timestamp))
        }
    }
    
    class NotebookDiffCallback : DiffUtil.ItemCallback<BookWithNotes>() {
        override fun areItemsTheSame(oldItem: BookWithNotes, newItem: BookWithNotes): Boolean {
            return oldItem.book.id == newItem.book.id
        }

        override fun areContentsTheSame(oldItem: BookWithNotes, newItem: BookWithNotes): Boolean {
            return oldItem.book == newItem.book && oldItem.notes == newItem.notes
        }
    }
}
