package com.fetch.fetch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hiring")
data class Hiring(@PrimaryKey var id: Int, var listId: Int, var name: String)