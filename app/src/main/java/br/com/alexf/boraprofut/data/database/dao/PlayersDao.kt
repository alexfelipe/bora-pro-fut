package br.com.alexf.boraprofut.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alexf.boraprofut.data.database.entities.PlayerEntity
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayersDao {

    @Query("SELECT * FROM PlayerEntity ORDER BY is_goal_keeper DESC, name")
    fun findAll(): Flow<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg entity: PlayerEntity)

    @Query("DELETE FROM PlayerEntity")
    suspend fun deleteAllPlayers()

    @Query("UPDATE PlayerEntity SET is_goal_keeper = :isGoalKeeper WHERE name = :playerName")
    suspend fun updateGoalKeeperField(playerName: String, isGoalKeeper: Boolean)

}