package com.ilnur.modimporter.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.ilnur.modimporter.database.AppDatabase
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.database.ModsCover
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    var context: Context,
    var db: AppDatabase
){

    suspend fun getLiked(): List<ModInfo>{
        val likedMods = db.modsDao().getLiked()
        Log.d("liked mods size", "${likedMods.size}")


        //list.forEach { println(it.toString()) }
        return likedMods
    }

    suspend fun updateMod(mod: ModInfo){
        when(mod.isFave){
            true -> {
                Log.d("update repos", "true ")
            }
            false -> {
                Log.d("update repos", "false ")
                db.modsDao().deleteByFilename(mod.filename)
            }
        }

    }
}