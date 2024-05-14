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
import androidx.navigation.fragment.navArgs
import coil.load
import com.roisul.jaran.MainActivity
import com.roisul.jaran.R
import com.roisul.jaran.databinding.FragmentEditProfileBinding
import com.roisul.jaran.model.Profile
import com.roisul.jaran.viewmodel.ProfileViewModel

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile), MenuProvider {

    private var editProfileBinding: FragmentEditProfileBinding? = null
    private val binding get() = editProfileBinding!!

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var currentProfile: Profile

    private var selectedImageUri: Uri? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                data?.data?.let {
                    selectedImageUri = it
                    binding.ivEditImageProfile.setImageURI(it)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        editProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        profileViewModel = (activity as MainActivity).profileViewModel
        currentProfile = args.profile!!

//        set current profile data
        binding.apply {
            etProfileNameFragmentEditProfile.setText(currentProfile.profileName)
            etProfileSocMedFragmentEditProfile.setText(currentProfile.profileSocMed)
            ivEditImageProfile.load(currentProfile.profileImageUri)
        }

        binding.ivEditGotoLocaltoUploadImage.setOnClickListener{
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun saveEditProfile() {
        val profileNameEdit = binding.etProfileNameFragmentEditProfile.text.toString().trim()
        val profileSocMedEdit = binding.etProfileSocMedFragmentEditProfile.text.toString().trim()
        val profileImageUriEdit = selectedImageUri?.toString() ?: currentProfile.profileImageUri

        if (profileNameEdit.isNotEmpty()){
            val profile = Profile(currentProfile.id, profileNameEdit, profileSocMedEdit, profileImageUriEdit)
            profileViewModel.updateProfile(profile)

            Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
            view?.findNavController()?.popBackStack(R.id.profileFragment, false)
        } else {
            Toast.makeText(requireContext(), "Minimal Ganti Nama Bang", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.update_profile_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.updateProfileMenu -> {
                saveEditProfile()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editProfileBinding = null
    }
}