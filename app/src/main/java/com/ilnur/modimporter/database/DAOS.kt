package com.ilnur.modimporter.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ModDao {
    @Query("SELECT * FROM mods")
    fun getMods(): LiveData<List<ModInfo>>

    @Query("SELECT * FROM mods WHERE isFave = :isFave")
    fun getLiked(isFave: Boolean = true): List<ModInfo>

    @Query("SELECT * FROM mods WHERE filename = :filename LIMIT 1")
    fun getModByFilename(filename: String): ModInfo?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mod: ModInfo)

    @Update
    fun update(mod: ModInfo)

    @Delete
    fun delete(mod: ModInfo)

    @Query("DELETE FROM mods WHERE filename = :filename")
    fun deleteByFilename(filename: String)
}