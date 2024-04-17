package br.com.alexf.boraprofut.models

import androidx.room.ColumnInfo

data class Player(
    val name: String,
    val level: Int = 0,
    @ColumnInfo("is_goal_keeper")
    val isGoalKeeper: Boolean = false
)