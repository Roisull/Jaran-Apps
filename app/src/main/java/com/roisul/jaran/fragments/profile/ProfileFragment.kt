package com.roisul.jaran.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.load
import com.roisul.jaran.MainActivity
import com.roisul.jaran.R
import com.roisul.jaran.databinding.FragmentProfileBinding
import com.roisul.jaran.model.Profile
import com.roisul.jaran.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile), MenuProvider {

    private var profileBinding: FragmentProfileBinding? = null
    private val binding get() = profileBinding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        profileViewModel = (activity as MainActivity).profileViewModel

        binding.btnAddProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_addProfileFragment)
        }

        replaceUiProfile()
    }

    private fun replaceUiProfile(){
        profileViewModel.getProfiles().observe(viewLifecycleOwner, Observer {
            it.firstOrNull()?.let {
                binding.apply {
                    tvProfileName.text = it.profileName
                    tvProfileSocMed.text = it.profileSocMed
                    ivProfile.load(it.profileImageUri)

                    currentProfile = it
                }
            }
        })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.editProfilMenu -> {
                currentProfile?.let {
                    val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(it)
                    view?.findNavController()?.navigate(action)
                }
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }
    private var currentProfile: Profile? = null
}

object ProfileBindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUri")
    fun loadImage(imageView: ImageView, imageUrl: String?){
        imageUrl?.let {
            imageView.load(it)
        }
    }
}
