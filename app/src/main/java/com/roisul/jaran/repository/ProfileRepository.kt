package com.roisul.jaran.repository

import com.roisul.jaran.database.NoteDatabase
import com.roisul.jaran.model.Profile

class ProfileRepository(private val db: NoteDatabase) {

    suspend fun insertProfile(profile: Profile) = db.getProfileDao().insertProfile(profile)
    suspend fun deleteProfile(profile: Profile) = db.getProfileDao().deleteProfile(profile)
    suspend fun updateProfile(profile: Profile) = db.getProfileDao().updateProfile(profile)
}
