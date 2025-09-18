package com.bookmark.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private val books: List<Book>,
    private val onBookClick: (Book) -> Unit,
    private val onAddPagesClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val pageCount: TextView = itemView.findViewById(R.id.pageCount)
        val chapterInfo: TextView = itemView.findViewById(R.id.chapterInfo)
        val actionButtonBackground: View = itemView.findViewById(R.id.actionButtonBackground)
        val addPagesIcon: ImageView = itemView.findViewById(R.id.addPagesIcon)
        val completedIcon: ImageView = itemView.findViewById(R.id.completedIcon)
        val pageCountOverlay: TextView = itemView.findViewById(R.id.pageCountOverlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        
        holder.bookTitle.text = book.title
        holder.progressBar.progress = book.getProgressPercentage()
        holder.pageCount.text = book.getProgressText()
        
        // Set chapter info
        if (book.currentChapter.isNotEmpty()) {
            holder.chapterInfo.text = book.getChapterText()
            holder.chapterInfo.visibility = View.VISIBLE
        } else {
            holder.chapterInfo.visibility = View.GONE
        }

        // Set progress bar color based on book's progress color
        val progressColor = when (book.progressColor) {
            "#E91E63" -> ContextCompat.getColor(holder.itemView.context, R.color.progress_purple)
            "#4CAF50" -> ContextCompat.getColor(holder.itemView.context, R.color.progress_green)
            "#FF9800" -> ContextCompat.getColor(holder.itemView.context, R.color.progress_orange)
            "#2196F3" -> ContextCompat.getColor(holder.itemView.context, R.color.progress_blue)
            "#F44336" -> ContextCompat.getColor(holder.itemView.context, R.color.progress_red)
            else -> ContextCompat.getColor(holder.itemView.context, R.color.progress_purple)
        }
        
        holder.progressBar.progressTintList = android.content.res.ColorStateList.valueOf(progressColor)

        // Configure action button based on book state
        if (book.isCompleted) {
            // Show checkmark for completed books
            holder.addPagesIcon.visibility = View.GONE
            holder.completedIcon.visibility = View.VISIBLE
            holder.pageCountOverlay.visibility = View.GONE
            holder.actionButtonBackground.background = ContextCompat.getDrawable(
                holder.itemView.context, 
                R.drawable.circle_action_button
            )
            holder.actionButtonBackground.backgroundTintList = 
                android.content.res.ColorStateList.valueOf(progressColor)
        } else {
            // Show + icon for books in progress
            holder.addPagesIcon.visibility = View.VISIBLE
            holder.completedIcon.visibility = View.GONE
            holder.pageCountOverlay.visibility = View.GONE
            holder.actionButtonBackground.background = ContextCompat.getDrawable(
                holder.itemView.context, 
                R.drawable.circle_action_button
            )
        }

        // Special case for the first book in the sample (with page overlay)
        if (position == 0 && book.title.contains("Vyaktitva")) {
            holder.addPagesIcon.visibility = View.GONE
            holder.pageCountOverlay.visibility = View.VISIBLE
            holder.pageCountOverlay.text = "30"
            holder.actionButtonBackground.backgroundTintList = 
                android.content.res.ColorStateList.valueOf(progressColor)
        }

        // Click listeners
        holder.itemView.setOnClickListener { onBookClick(book) }
        holder.actionButtonBackground.setOnClickListener { onAddPagesClick(book) }
    }

    override fun getItemCount() = books.size
}