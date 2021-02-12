package com.ilnur.modimporter.ui.modinfo

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.repository.HomeRepository
import com.ilnur.modimporter.repository.ModinfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModinfoViewModel @Inject constructor(
    private val repository: ModinfoRepository
) : ViewModel(), LifecycleObserver {


    private val _mod = MutableLiveData<ModInfo>()

    val mod: LiveData<ModInfo> get() = _mod


    fun getModInfo(filename: String){
        CoroutineScope(Dispatchers.IO).launch{ _mod.postValue(repository.getMod(filename)) }
    }


}