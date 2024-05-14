package com.roisul.jaran

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.roisul.jaran.database.NoteDatabase
import com.roisul.jaran.repository.NoteRepository
import com.roisul.jaran.repository.ProfileRepository
import com.roisul.jaran.viewmodel.NoteViewModel
import com.roisul.jaran.viewmodel.NoteViewModelFactory
import com.roisul.jaran.viewmodel.ProfileViewModel
import com.roisul.jaran.viewmodel.ProfileViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        Log Activity Started
        Log.d("MainActivity", "onCreated: Memulai Activity")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()
    }

    private fun setupViewModel(){

//        setup Note ViewModel
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]

//        setup Profile ViewModel
        val profileRepository = ProfileRepository(NoteDatabase(this))
        val viewModelProviderFactoryProfile = ProfileViewModelFactory(application, profileRepository)
        profileViewModel = ViewModelProvider(this, viewModelProviderFactoryProfile)[ProfileViewModel::class.java]

    }
}