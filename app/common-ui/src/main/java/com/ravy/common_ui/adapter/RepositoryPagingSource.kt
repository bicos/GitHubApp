package com.ravy.common_ui.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ravy.data.dto.SearchResult

class RepositoryPagingSource(
    private val func: suspend (page: Int) -> SearchResult,
    private val totalCount: MutableLiveData<Long>? = null
) : PagingSource<Int, RepositoryAdapterItem>() {

    var remainCount = 0L

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryAdapterItem> {
        return try {
            val currentPage = params.key ?: 0
            val response = func.invoke(currentPage)
            totalCount?.value = response.totalCount
            remainCount =
                if (currentPage == 0)
                    (response.totalCount - response.items.size).coerceAtLeast(0)
                else
                    (remainCount - response.items.size).coerceAtLeast(0)
            val nextPage =
                if (response.incompleteResults || response.items.isEmpty() || remainCount == 0L)
                    null
                else
                    currentPage + 1
            return LoadResult.Page(
                data = response.items.map { RepositoryAdapterItem(it) },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Throwable) {
            Log.e("RepositoryPagingSource", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryAdapterItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PER_PAGE = 30
    }
}