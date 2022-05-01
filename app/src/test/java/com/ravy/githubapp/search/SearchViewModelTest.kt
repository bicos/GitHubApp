package com.ravy.githubapp.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.ravy.data.repo.RepoRepository
import com.ravy.githubapp.util.MainCoroutineRule
import com.ravy.githubapp.util.getOrAwaitValue
import com.ravy.search.SearchViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule(order = 0)
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    private fun initViewModel(
        repo: RepoRepository = mockk(relaxed = true)
    ) = SearchViewModel(repo)

    @Test
    fun test_if_error_occurred_error_view_will_be_show() {
        val viewModel = initViewModel()
        val error = LoadState.Error(IllegalStateException("test"))
        viewModel.changeLoadStates(
            CombinedLoadStates(
                refresh = error,
                prepend = mockk(relaxed = true),
                append = mockk(relaxed = true),
                source = mockk(relaxed = true)
            )
        )
        assert(viewModel.isShowError.getOrAwaitValue())
        assert(viewModel.errorMessage.getOrAwaitValue() == error.error.localizedMessage)
    }

    @Test
    fun test_if_input_null_error_toast_will_be_show() {
        val viewModel = initViewModel()
        viewModel.clickSearch(null)
        assert(viewModel.showToast.getOrAwaitValue() == "검색어를 입력해 주세요")
    }

    @Test
    fun test_if_input_empty_text_error_toast_will_be_show() {
        val viewModel = initViewModel()
        viewModel.clickSearch("")
        assert(viewModel.showToast.getOrAwaitValue() == "검색어를 입력해 주세요")
    }

    @Test
    fun test_if_total_count_changed_total_count_txt_will_be_change() {
        val viewModel = initViewModel()
        viewModel.totalCount.value = 1000
        assert(viewModel.totalCountTxt.getOrAwaitValue() == "총 1,000개 검색됨")
    }

}
