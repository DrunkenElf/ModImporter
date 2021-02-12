package com.ilnur.modimporter.ui.home

import android.Manifest
import android.content.ContentValues.TAG
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
import com.ilnur.modimporter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  homeViewModel =
        //     ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        homeViewModel.mods.observe(viewLifecycleOwner, {
            binding.recyclerHome.adapter = ModsAdapter(
                requireContext(),
                homeViewModel.mods.value ?: emptyList(),
                { selectedMod(it) },
                { likedMod(it) },
            )
        })

        binding.recyclerHome.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHome.isNestedScrollingEnabled = false
        binding.recyclerHome.adapter =
            ModsAdapter(
                requireContext(),
                homeViewModel.mods.value ?: emptyList(),
                { selectedMod(it) },
                { likedMod(it) }
            )


        return binding.root
    }

    private fun likedMod(mod: ModInfo) {
        homeViewModel.updateMod(mod)
    }

    private fun selectedMod(mod: ModInfo) {
        homeViewModel.push(mod)
        Toast.makeText(context, "selected ${mod.filename}", Toast.LENGTH_SHORT).show()
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                //Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                Log.d("perm_den", "android r")
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            Log.d(
                TAG,
                "After getting permission: " + Manifest.permission.WRITE_EXTERNAL_STORAGE + " " + ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == 1
            ) {
                startModInfoAct(mod)
                homeViewModel.pop()
            }

        } else {
            startModInfoAct(mod)
            homeViewModel.pop()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("request code", requestCode.toString())
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    homeViewModel.pop()?.let {
                        startModInfoAct(it)
                    }
            }
        }
    }

    private fun startModInfoAct(mod: ModInfo) {
        val action = HomeFragmentDirections.actionNavHomeToNavModinfo(mod.filename)

        this.findNavController().navigate(action)
    }

    ///data/user/0/com.ilnur.modimporter/files/moddd1
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("onDetach", "homefrag")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onREssume", "homefrag")
        homeViewModel.onContinue()
    }
}