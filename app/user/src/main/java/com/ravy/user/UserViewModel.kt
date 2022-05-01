package com.ravy.user

import androidx.lifecycle.*
import androidx.paging.*
import com.ravy.common_ui.Event
import com.ravy.common_ui.adapter.RepositoryPagingSource
import com.ravy.common_ui.util.filter
import com.ravy.data.pref.AppPreference
import com.ravy.data.repo.RepoRepository
import com.ravy.data.repo.UserRepository
import com.ravy.data.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val pref: AppPreference,
    private val userRepo: UserRepository,
    private val repoRepo: RepoRepository
) : ViewModel() {

    private val _userId = MutableLiveData(pref.userId)
    val userId: LiveData<String> = _userId

    val isShowUser = _userId.map {
        !it.isNullOrEmpty()
    }

    val userInfo: LiveData<User?> = _userId.switchMap {
        return@switchMap if (it.isNullOrEmpty()) {
            MutableLiveData(null)
        } else {
            flow {
                emit(userRepo.getUser(it))
            }.onStart {
                isShowError.value = false
            }.catch {
                isShowError.value = true
            }.asLiveData()
        }
    }

    val items = _userId
        .filter { !it.isNullOrBlank() }
        .switchMap { userId ->
            Pager(
                PagingConfig(pageSize = RepositoryPagingSource.PER_PAGE)
            ) {
                RepositoryPagingSource({
                    repoRepo.searchReposByUser(
                        userId = userId,
                        page = it
                    )
                })
            }.liveData
        }.cachedIn(viewModelScope)

    val avatar = userInfo.map {
        it?.avatarUrl
    }

    val userName = userInfo.map {
        it?.name
    }

    val desc = userInfo.map {
        "followers ${it?.followers ?: 0} · following ${it?.following ?: 0}"
    }

    val isShowError = MutableLiveData(false)

    private val _loadStates = MutableLiveData<CombinedLoadStates>()
    val isShowRepoError = _loadStates.map {
        it.refresh is LoadState.Error
    }
    val repoErrorMessage = _loadStates.map {
        (it.refresh as? LoadState.Error)?.error?.localizedMessage
    }

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    fun clickBtnConfirm(id: String?) {
        if (id.isNullOrBlank()) {
            _showToast.value = "유저 id를 입력해 주세요"
            return
        }
        pref.userId = id
        _userId.value = id
    }

    private val _navigateUrl = MutableLiveData<Event<String?>>()
    val navigateUrl: LiveData<Event<String?>> = _navigateUrl

    fun clickUserInfo(user: User?) {
        _navigateUrl.value = Event(user?.htmlUrl)
    }

    fun clickRetryBtn() {
        _userId.value = _userId.value
    }

    private val _retry: MutableLiveData<Unit> = MutableLiveData()
    val retry : LiveData<Unit> = _retry

    fun clickRepoRetryBtn() {
        _retry.value = Unit
    }

    fun clickClearUserInfo() {
        pref.userId = ""
        _userId.value = ""
    }

    fun changeLoadStates(loadStates: CombinedLoadStates) {
        _loadStates.value = loadStates
    }
}