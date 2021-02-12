package com.ilnur.modimporter.ui.favourites

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.repository.FavouritesRepository
import com.ilnur.modimporter.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: FavouritesRepository
) : ViewModel(), LifecycleObserver {

    private val _mods = MutableLiveData<List<ModInfo>>().apply {
        CoroutineScope(Dispatchers.IO).launch{ this@apply.postValue(repository.getLiked()) }
    }

    val mods: LiveData<List<ModInfo>> get() = _mods

    fun onContinue() = CoroutineScope(Dispatchers.IO).launch{ _mods.postValue(repository.getLiked()) }


    fun updateMod(mod: ModInfo){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateMod(mod)
            Log.d("before del", _mods.value?.size.toString())
            val list: MutableList<ModInfo>? = mods.value?.toMutableList()
            list?.remove(mod.copy(isFave = true))
            _mods.postValue(list)
            Log.d("after del", _mods.value?.size.toString())
            Log.d("update viewmode", mod.filename)
            /* _mods.value
             _mods.postValue(_mods.value?.map {
                 return@map if (it.filename == mod.filename){
                     mod
                 } else it
                 }
             )*/
        }
    }
}