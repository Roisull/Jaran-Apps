package com.roisul.jaran.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.roisul.jaran.model.Note
import com.roisul.jaran.model.Profile

val MIGRATION_1_2: Migration = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS profiles ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "profileName TEXT NOT NULL," +
                "profileSocMed TEXT NOT NULL," +
                "profileImageUri TEXT NOT NULL)"
        )
    }
}

@Database(entities = [Note::class, Profile::class], version = 2)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getProfileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).addMigrations(MIGRATION_1_2).build()
    }
}