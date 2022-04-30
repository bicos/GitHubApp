package com.ravy.common_ui.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ravy.data.vo.Repository
import com.ravy.data.vo.User

class RepositoryAdapterViewModel : ViewModel() {

    private val _clickRepository = MutableLiveData<Repository>()
    val clickRepository: LiveData<Repository> = _clickRepository

    private val _clickOwner = MutableLiveData<User>()
    val clickOwner: LiveData<User> = _clickOwner

    fun clickRepository(repo: Repository) {
        _clickRepository.value = repo
    }

    fun clickOwner(user: User) {
        _clickOwner.value = user
    }

}