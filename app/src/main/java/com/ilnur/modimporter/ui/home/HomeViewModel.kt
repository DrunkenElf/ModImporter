package com.ilnur.modimporter.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel(), LifecycleObserver {


    private val _mods = MutableLiveData<List<ModInfo>>().apply {
        CoroutineScope(Dispatchers.IO).launch{ this@apply.postValue(repository.getMods()) }
    }

    val mods: LiveData<List<ModInfo>> get() = _mods

    val _selectedMod = MutableLiveData<Stack<ModInfo>>(Stack())
    val selectedMod: LiveData<Stack<ModInfo>> get() = _selectedMod

    fun push(mod: ModInfo){
        _selectedMod.value?.push(mod).also {
            Log.d("push val", it?.filename.toString() + " and size after : ${_selectedMod.value?.size}")
        }
    }

    fun pop(): ModInfo? {
        return _selectedMod.value?.pop().also {
            Log.d("pop return val", it?.filename.toString() + " and size after : ${_selectedMod.value?.size}")
        }
    }


    fun onContinue() = CoroutineScope(Dispatchers.IO).launch{
        val temp = repository.getMods()
        var isChanged = false
        _mods.value?.forEachIndexed { index, modInfo ->
            if (modInfo.isFave != temp[index].isFave)
                isChanged = true
        }
        if (isChanged)
            _mods.postValue(repository.getMods())
    }

    fun updateMod(mod: ModInfo){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateMod(mod)
            Log.d("update viewmode", mod.toString())
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