package br.com.alexf.futzinmaroto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexf.futzinmaroto.data.database.dao.PlayersDao
import br.com.alexf.futzinmaroto.data.database.entities.PlayerEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [PlayerEntity::class]
)
abstract class FutzinMarotoDatabase : RoomDatabase(){

    abstract fun playerDao() : PlayersDao

}