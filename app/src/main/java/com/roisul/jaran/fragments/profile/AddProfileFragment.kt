package com.roisul.jaran.fragments.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.roisul.jaran.MainActivity
import com.roisul.jaran.R
import com.roisul.jaran.databinding.FragmentAddProfileBinding
import com.roisul.jaran.model.Profile
import com.roisul.jaran.viewmodel.ProfileViewModel

class AddProfileFragment : Fragment(R.layout.fragment_add_profile), MenuProvider {

    private var addProfileBinding: FragmentAddProfileBinding? = null
    private val binding get() = addProfileBinding!!

    private var selectedImageUri: Uri? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var addProfileView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

//                pengambilan data gambar dari gallery
                data?.data?.let {uri ->
                    selectedImageUri = uri
                    binding.ivAddImageProfile.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        addProfileBinding = FragmentAddProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        profileViewModel = (activity as MainActivity).profileViewModel
        addProfileView = view

        binding.ivGotoLocaltoUploadImage.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun saveProfile(view: View) {
        val addNameProfile = binding.etProfileNameFragmentAddProfile.text.toString().trim()
        val addSocMedProfile = binding.etProfileSocMedFragmentAddProfile.text.toString().trim()
        val profileImageUri = selectedImageUri.toString()

        if(addNameProfile.isNotEmpty()) {
            val profile = Profile(0, addNameProfile, addSocMedProfile, profileImageUri)
            profileViewModel.addProfile(profile)

            Toast.makeText(addProfileView.context, "Profile Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.profileFragment, false)
        } else {
            Toast.makeText(addProfileView.context, "Minimal Masukin Nama Nama Dung", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_profile_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.saveProfileMenu -> {
                saveProfile(addProfileView)
                true
            } else -> false
        }
    }
}
