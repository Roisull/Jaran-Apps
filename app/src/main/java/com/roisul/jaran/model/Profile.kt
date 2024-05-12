package com.roisul.jaran.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "profiles")
@Parcelize
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val profileName: String,
    val profileSocMed: String,
    val profileImageUri: String
): Parcelable
