package com.roisul.jaran.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roisul.jaran.repository.ProfileRepository

class ProfileViewModelFactory(val app: Application, private val profileRepository: ProfileRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(app, profileRepository) as T
    }
}