package com.bookmark.app

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithNotes(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val notes: List<Note>
)

data class BookWithSessions(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookId"
    )
    val sessions: List<ReadingSession>
)
