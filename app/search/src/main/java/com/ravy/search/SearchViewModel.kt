package com.ravy.search

import android.net.Uri
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.core.net.toUri
import androidx.lifecycle.*
import androidx.paging.*
import com.ravy.data.api.GitApiService
import com.ravy.data.repo.RepoRepository
import com.ravy.common_ui.adapter.RepositoryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: RepoRepository
) : ViewModel() {

    val keyword: MutableLiveData<String> = MutableLiveData()

    @VisibleForTesting
    val totalCount: MutableLiveData<Long> = MutableLiveData()

    val totalCountTxt = totalCount
        .map { "총 ${NumberFormat.getNumberInstance().format(it)}개 검색됨" }

    private val _requestSearchEvent: MutableLiveData<String> = MutableLiveData()
    val requestSearchEvent: LiveData<String> = _requestSearchEvent

    val items = _requestSearchEvent
        .switchMap { query ->
            Pager(
                PagingConfig(pageSize = RepositoryPagingSource.PER_PAGE)
            ) {
                RepositoryPagingSource({
                    repo.searchRepos(
                        query = query,
                        page = it
                    )
                }, totalCount)
            }.liveData
        }.cachedIn(viewModelScope)

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    fun clickSearch(keyword: String?) {
        if (keyword.isNullOrBlank()) {
            _showToast.value = "검색어를 입력해 주세요"
            return
        }

        keyword.let { _requestSearchEvent.value = it }
    }

    private val _loadStates = MutableLiveData<CombinedLoadStates>()

    val isShowLoading = _loadStates.map {
        it.refresh is LoadState.Loading
    }
    val isShowError = _loadStates.map {
        it.refresh is LoadState.Error
    }
    val errorMessage = _loadStates.map {
        (it.refresh as? LoadState.Error)?.error?.localizedMessage
    }

    private val _retry = MutableLiveData<Unit>()
    val retry: LiveData<Unit> = _retry

    fun changeLoadStates(loadStates: CombinedLoadStates) {
        _loadStates.value = loadStates
    }

    fun clickRetryBtn() {
        _retry.value = Unit
    }
}