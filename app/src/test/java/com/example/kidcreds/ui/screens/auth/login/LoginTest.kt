package com.example.kidcreds.ui.screens.auth.login

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.kidcreds.data.datastore.KidCredsPrefDatastore
import com.example.kidcreds.data.local.table.UserEntity
import com.example.kidcreds.data.repository.UserRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class LoginTest {
    private val testDispatcher = StandardTestDispatcher()
    private val userRepo = mockk<UserRepo>()
    private var prefDatastore = mockk<KidCredsPrefDatastore>(relaxed = true)
    private lateinit var viewModel: LogInViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        viewModel = LogInViewModel(
            userRepo = userRepo,
            kidCredsPrefDatastore = prefDatastore
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
    fun `login with correct credentials emits Success event`() = runTest {
        // 1. Arrange: Define what the mock should return
        val testEmail = "test@gmail.com"
        val testPass = "12345"
        val mockUser = UserEntity(email = testEmail, password = testPass, name = "Tiku")

        // Use coEvery for suspend functions
        coEvery { userRepo.loginUserAccount(testEmail, testPass) } returns mockUser
        // Start collecting events before acting
        val events = mutableListOf<LogInEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.event.collect { events.add(it) }
        }
        // 2. Act
        viewModel.loginUserAccount(testEmail, testPass)
        testDispatcher.scheduler.advanceUntilIdle()

        // 3. Assert: Check the Flow event
        assertThat(events).isNotEmpty()
        val event = events.first()
        assertThat(event).isInstanceOf(LogInEvent.OnLoginSuccess::class.java)
        assertThat((event as LogInEvent.OnLoginSuccess).message).contains( "login success")

        // 4. Verify: Check if saveLoginStatus was actually called on the DataStore
        coVerify { prefDatastore.saveLoginStatus(true) }
        collectJob.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `login with wrong credentials emits Error event`() = runTest {
        // Arrange: Simulate a null return (failed login)
        coEvery { userRepo.loginUserAccount(any(), any()) } returns null

        val events = mutableListOf<LogInEvent>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.event.collect { events.add(it) }
        }
        // Act
        viewModel.loginUserAccount("wrong@email.com", "wrong_pass")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertThat(events).isNotEmpty()
        val event = events.first()
        assertThat(event).isInstanceOf(LogInEvent.ShowError::class.java)

        // Verify DataStore was NEVER called for wrong credentials
        coVerify(exactly = 0) { prefDatastore.saveLoginStatus(any()) }
        collectJob.cancel()
    }
}