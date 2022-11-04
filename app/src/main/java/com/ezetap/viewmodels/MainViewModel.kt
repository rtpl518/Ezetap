package com.ezetap.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ezetap.base.BaseViewModel
import com.ezetap.data.MainRepository
import com.ezetap.network.data.CustomUIResponse
import com.ezetap.network.data.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    private val _customUiLiveData = MutableLiveData<CustomUIResponse>()
    val customUiLiveData: LiveData<CustomUIResponse> = _customUiLiveData

    private val _errorLiveData = MutableLiveData<ErrorResponse>()
    val errorLiveData: LiveData<ErrorResponse> = _errorLiveData

    fun fetchCustomUI() {
        viewModelScope.launch(Dispatchers.IO) {
            with(mainRepository.fetchCustomUI()) {
                result
                    ?.let { _customUiLiveData.postValue(it as? CustomUIResponse) }
                    ?: kotlin.run {
                        errorResponse
                            ?.let { _errorLiveData.postValue(it) }
                    }
            }
        }
    }
}