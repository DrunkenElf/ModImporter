package com.ilnur.modimporter.ui.modinfo

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilnur.modimporter.R
import com.ilnur.modimporter.adapters.ImagePagerAdapter
import com.ilnur.modimporter.copyAsset
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.databinding.FragmentModinfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import android.content.pm.PackageManager
import android.net.Uri


@AndroidEntryPoint
class ModinfoFragment : Fragment() {

    val minePackage = "com.mojang.minecraftpe"
    private var _binding: FragmentModinfoBinding? = null

    private val binding get() = _binding!!

    val viewModel: ModinfoViewModel by viewModels()

    var filename: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModinfoBinding.inflate(inflater, container, false)

        filename = arguments?.getString("filename")
        Log.d(this.javaClass.name, filename ?: "nullll")
        viewModel.getModInfo(filename!!)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()

        viewModel.mod.observe(viewLifecycleOwner, {
            binding.detailedTitle.text = it.title
            binding.detailedDesc.text = it.desc.replace("&#39;", "'")
            //binding.btnInstall.text = if (it.isFave) resources.getString(R.string.) else "install"
            binding.btnInstall.text = resources.getString(R.string.btn_download)
            binding.btnInstall.setOnClickListener {
                Toast.makeText(requireContext(), "btn clicked", Toast.LENGTH_SHORT).show()
                when (binding.btnInstall.text) {
                    resources.getString(R.string.btn_install) -> {
                        if (isPackageInstalled(minePackage, context?.packageManager))
                            importMod(viewModel.mod.value!!)
                        else
                            openAppInPlayStore(minePackage)
                    }
                    else -> {
                        binding.btnInstall.text = resources.getString(R.string.btn_downloading)
                        lifecycleScope.launch(Dispatchers.Main) {
                            binding.btnInstall.isClickable = false
                            delay(2000)
                            binding.btnInstall.text = resources.getString(R.string.btn_install)
                            binding.btnInstall.isClickable = true
                        }
                    }
                }
            }

            Log.d("img path", it.imgPath)
            ViewCompat.setTransitionName(binding.pagerModinfo, "image_${it.filename}")
            binding.pagerModinfo.adapter = ImagePagerAdapter(
                requireContext(), listOf(
                    it.imgPath,
                    "1606124632_play-minecraft-as-mutant-zombie_1.png"
                )
            )
            binding.pagerModinfo.apply {
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(10))
                setPageTransformer(compositePageTransformer)

            }
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }

        })

    }

    fun importMod(mod: ModInfo){
        val file = File(requireContext().getExternalFilesDir(null), mod.filename)
        if (file.exists()) file.delete()
        file.createNewFile()
        //if (!file.exists()) file.mkdirs()
        if (file.exists()) file.delete()
        file.createNewFile()
        Log.d("path ", file.path)

        Log.d(ContentValues.TAG, "Already has permission to write to external storage");

        //copyAssetFolder(requireContext().assets, "ChrismassNigh.mcaddon", file.absolutePath)
        copyAsset(requireContext().assets, "files/${mod.filename}", file.absolutePath)

        Log.d("packagenameOrig ", requireContext().packageName)

        val uri = FileProvider.getUriForFile(
            requireActivity(),
            "${requireContext().packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.`package` = "com.mojang.minecraftpe"
        intent.setDataAndType(uri, "file/*")
        startActivity(intent)
    }

    private fun isPackageInstalled(packageName: String, packageManager: PackageManager?): Boolean {
        return try {
            packageManager!!.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        } catch (npe: NullPointerException){
            false
        }
    }

    private fun openAppInPlayStore(appPackageName: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (exception: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }
}

