package com.bookmark.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(
    private val onBookClick: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.bookCover)
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val circularStreak: CircularStreakView = itemView.findViewById(R.id.circularStreak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_dark, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        
        holder.bookTitle.text = book.title
        holder.bookAuthor.text = book.author
        holder.progressBar.progress = book.getProgressPercentage()
        
        // Load book cover with Glide
        if (book.coverUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(book.coverUrl)
                .placeholder(R.color.surface_light)
                .into(holder.bookCover)
        } else {
            holder.bookCover.setImageResource(R.color.surface_light)
        }
        
        // Set streak indicator (placeholder - will be calculated from reading sessions)
        holder.circularStreak.setStreakData("D0", 0f, R.color.streak_red)
        
        // Click listener
        holder.itemView.setOnClickListener { onBookClick(book) }
    }
    
    class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}