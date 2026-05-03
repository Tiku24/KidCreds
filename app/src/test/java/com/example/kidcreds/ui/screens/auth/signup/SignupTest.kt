package com.example.kidcreds.ui.screens.auth.signup

import android.util.Log
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import com.example.kidcreds.ui.screens.auth.login.LogInEvent
import com.example.kidcreds.ui.screens.auth.login.LogInViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SignupTest {
    private val testDispatcher = StandardTestDispatcher()
    private val userRepo = mockk<UserRepo>()
    private lateinit var viewModel: SignUpViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        viewModel = SignUpViewModel(
            userRepo = userRepo
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `signup with correct credentials emits Success event`() = runTest {
        // 1. Arrange: Define what the mock should return
        val testName = "Rahul"
        val testEmail = "test@gmail.com"
        val testPass = "12345"
        val mockUser = UserEntity(email = testEmail, password = testPass, name = testName)

        // Use coEvery for suspend functions
        coEvery { userRepo.updateCreatUserAccount(mockUser) } returns Unit
        // Start collecting events before acting
        val events = mutableListOf<SignUpEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.event.collect { events.add(it) }
        }
        // 2. Act
        viewModel.updateCreateUserAccount(mockUser)
        testDispatcher.scheduler.advanceUntilIdle()

        // 3. Assert: Check the Flow event
        assertThat(events).isNotEmpty()
        val event = events.first()
        assertThat(event).isInstanceOf(SignUpEvent.OnSignUpSuccess::class.java)
        assertThat((event as SignUpEvent.OnSignUpSuccess).message).contains( "Account created successfully")

        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `signup with wrong credentials emits Error event`() = runTest {
        // Arrange: Simulate a null return (failed login)
        coEvery { userRepo.updateCreatUserAccount(any()) } returns Unit

        val events = mutableListOf<SignUpEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.event.collect { events.add(it) }
        }
        // Act
        viewModel.updateCreateUserAccount(UserEntity(name = "", email = "", password = ""))
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertThat(events).isNotEmpty()
        val event = events.first()
        assertThat(event).isInstanceOf(SignUpEvent.ShowError::class.java)
        collectJob.cancel()
    }
}
