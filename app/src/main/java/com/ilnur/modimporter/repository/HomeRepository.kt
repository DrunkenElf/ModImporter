package com.ilnur.modimporter.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.ilnur.modimporter.database.AppDatabase
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.database.ModsCover
import javax.inject.Inject

class HomeRepository @Inject constructor(
    var context: Context,
    var db: AppDatabase
) {

    suspend fun getMods(): List<ModInfo>{
        val jsonString = context.assets.open("data.json")
            .bufferedReader().use { it.readText() }
        val modsJson = Gson().fromJson(jsonString, ModsCover::class.java).google.mods

        val modsDao = db.modsDao()
        var temp: ModInfo?

        val list =  modsJson.map {
            temp = modsDao.getModByFilename(it.filename)
            Log.d("temp", temp.toString())
            if (temp!= null && temp!!.isFave)
                return@map it.copy(isFave = true)
            else
                return@map it
        }
        //list.forEach { println(it.toString()) }
        return list
    }

    suspend fun updateMod(mod: ModInfo){

        when(mod.isFave){
            true -> {
                Log.d("update repos", "true ")
                db.modsDao().insert(mod)
            }
            false -> {
                Log.d("update repos", "false ")
                db.modsDao().deleteByFilename(mod.filename)
            }
        }

    }



}