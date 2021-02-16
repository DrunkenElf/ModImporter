package com.ilnur.modimporter.ui.favourites

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilnur.modimporter.adapters.ModsAdapter
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.databinding.FragmentFavouritesBinding
import com.ilnur.modimporter.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        viewModel.mods.observe(viewLifecycleOwner, {
            binding.recyclerFavourites.adapter = ModsAdapter(
                requireContext(),
                viewModel.mods.value ?: emptyList(),
                { selectedMod(it) },
                { likedMod(it) },
            )
        })

        binding.recyclerFavourites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavourites.isNestedScrollingEnabled = false
        binding.recyclerFavourites.adapter =
            ModsAdapter(
                requireContext(),
                viewModel.mods.value ?: emptyList(),
                { selectedMod(it) },
                { likedMod(it) }
            )


        return binding.root
    }

    private fun likedMod(mod: ModInfo) {
        viewModel.updateMod(mod)
    }

    private fun selectedMod(mod: ModInfo) {
        Toast.makeText(context, "selected ${mod.filename}", Toast.LENGTH_SHORT).show()
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                //Manifest.permission.MANAGE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_DENIED
        ) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                Log.d("perm_den", "android r")
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            Log.d(
                ContentValues.TAG,
                "After getting permission: " + Manifest.permission.WRITE_EXTERNAL_STORAGE + " " + ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            );
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            )
                startModInfoAct(mod)

        } else {
            startModInfoAct(mod)
        }
    }

    private fun startModInfoAct(mod: ModInfo) {
        val action = FavouritesFragmentDirections.actionNavFavToNavModinfo(mod.filename)

        this.findNavController().navigate(action)
    }

    ///data/user/0/com.ilnur.modimporter/files/moddd1
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.onContinue()
    }
}