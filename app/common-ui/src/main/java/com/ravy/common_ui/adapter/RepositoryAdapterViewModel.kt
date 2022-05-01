package com.ravy.common_ui.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ravy.common_ui.Event
import com.ravy.data.vo.Repository
import com.ravy.data.vo.User

class RepositoryAdapterViewModel : ViewModel() {

    private val _clickRepository = MutableLiveData<Event<Repository>>()
    val clickRepository: LiveData<Event<Repository>> = _clickRepository

    private val _clickOwner = MutableLiveData<Event<User>>()
    val clickOwner: LiveData<Event<User>> = _clickOwner

    fun clickRepository(repo: Repository) {
        _clickRepository.value = Event(repo)
    }

    fun clickOwner(user: User) {
        _clickOwner.value = Event(user)
    }

}