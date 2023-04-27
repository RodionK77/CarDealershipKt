package com.example.cardealershipkt

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE CarItem ADD COLUMN birthday INTEGER DEFAULT 0 NOT NULL")
    }
}