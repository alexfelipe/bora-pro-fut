package br.com.alexf.boraprofut.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alexf.boraprofut.data.database.dao.PlayersDao
import br.com.alexf.boraprofut.data.database.entities.PlayerEntity

@Database(
    version = 2,
    exportSchema = true,
    entities = [PlayerEntity::class],
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class BoraProFutDatabase : RoomDatabase(){

    abstract fun playerDao() : PlayersDao

}