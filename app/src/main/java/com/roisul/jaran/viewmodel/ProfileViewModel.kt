package com.roisul.jaran.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.roisul.jaran.model.Profile
import com.roisul.jaran.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application, private val profileRepository: ProfileRepository): AndroidViewModel(app) {
    fun addProfile(profile: Profile) =
        viewModelScope.launch {
            profileRepository.insertProfile(profile)
        }

    fun deleteProfile(profile: Profile) =
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
        }

    fun updateProfile(profile: Profile) =
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
        }

    fun getProfiles(): LiveData<List<Profile>> = profileRepository.getAllProfile()
}
