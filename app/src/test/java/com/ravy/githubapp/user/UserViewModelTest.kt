package com.ravy.githubapp.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ravy.data.pref.AppPreference
import com.ravy.data.repo.RepoRepository
import com.ravy.data.repo.UserRepository
import com.ravy.data.vo.User
import com.ravy.githubapp.util.MainCoroutineRule
import com.ravy.githubapp.util.getOrAwaitValue
import com.ravy.user.UserViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    @get:Rule(order = 0)
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 1)
    var mainCoroutineRule = MainCoroutineRule()

    private fun initViewModel(
        pref: AppPreference = mockk(relaxed = true),
        userRepo: UserRepository = mockk(relaxed = true),
        repoRepo: RepoRepository = mockk(relaxed = true)
    ) = UserViewModel(pref, userRepo, repoRepo)

    @Test
    fun test_if_user_id_empty_user_info_will_not_be_shown() {
        val pref: AppPreference = mockk(relaxed = true)
        every { pref.userId }.returns("")
        val viewModel = initViewModel(pref = pref)

        Assert.assertTrue(!viewModel.isShowUser.getOrAwaitValue())
    }

    @Test
    fun test_if_user_id_not_empty_user_info_will_be_shown() {
        val pref: AppPreference = mockk(relaxed = true)
        every { pref.userId }.returns("test")
        val viewModel = initViewModel(pref = pref)

        Assert.assertTrue(viewModel.isShowUser.getOrAwaitValue())
    }

    @Test
    fun test_if_user_info_is_null_error_view_will_be_shown() {
        val userId = "test"
        val pref: AppPreference = mockk(relaxed = true)
        every { pref.userId }.returns(userId)
        val userRepo: UserRepository = mockk(relaxed = true)
        coEvery { userRepo.getUser(userId) }.throws(Throwable())

        val viewModel = initViewModel(pref, userRepo)
        viewModel.userInfo.observeForever { }
        Assert.assertTrue(viewModel.isShowError.getOrAwaitValue())
    }

    @Test
    fun test_if_get_user_info_success_user_info_will_be_show_correctly() {
        val userId = "test"
        val pref: AppPreference = mockk(relaxed = true)
        every { pref.userId }.returns(userId)
        val userRepo: UserRepository = mockk(relaxed = true)
        val user = User(
            login = userId,
            id = 100000L,
            name = "test",
            avatarUrl = "image.test.com",
            htmlUrl = "test.com",
            followers = 10,
            following = 10
        )
        coEvery { userRepo.getUser(userId) }.returns(user)

        val viewModel = initViewModel(pref, userRepo)
        val result = viewModel.userInfo.getOrAwaitValue()
        Assert.assertTrue(result != null)
        Assert.assertTrue(result == user)
        Assert.assertTrue(viewModel.avatar.getOrAwaitValue() == "image.test.com")
        Assert.assertTrue(viewModel.desc.getOrAwaitValue() == "followers 10 · following 10")
    }

    @Test
    fun test_if_attempt_to_input_empty_user_id_toast_message_will_be_show() {
        val viewModel = initViewModel()
        viewModel.clickBtnConfirm(null)
        Assert.assertTrue(viewModel.showToast.getOrAwaitValue() == "유저 id를 입력해 주세요")
    }
}