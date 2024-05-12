package com.roisul.jaran.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.roisul.jaran.MainActivity
import com.roisul.jaran.R
import com.roisul.jaran.databinding.FragmentProfileBinding
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
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.editProfilMenu -> {
                view?.findNavController()?.navigate(R.id.action_profileFragment_to_editProfileFragment)
                true
            } else -> false
        }
    }
}