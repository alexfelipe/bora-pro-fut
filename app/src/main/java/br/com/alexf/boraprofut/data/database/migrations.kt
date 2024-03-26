package br.com.alexf.boraprofut.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE PlayerEntity ADD COLUMN is_goal_keeper INTEGER DEFAULT 0")
    }
}