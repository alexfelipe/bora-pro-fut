package br.com.alexf.futzinmaroto.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PlayerEntity(
    @PrimaryKey
    val name: String,
    val level: Int,
)