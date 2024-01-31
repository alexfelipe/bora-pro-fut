package br.com.alexf.boraprofut.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexf.boraprofut.data.database.dao.PlayersDao
import br.com.alexf.boraprofut.data.database.entities.PlayerEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [PlayerEntity::class]
)
abstract class BoraProFutDatabase : RoomDatabase(){

    abstract fun playerDao() : PlayersDao

}