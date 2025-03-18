package com.fetch.fetch.data.repository

import com.fetch.fetch.data.model.Hiring
import com.fetch.fetch.data.remote.FetchService
import com.fetch.fetch.utils.Logger
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FetchRepositoryTest {

    private lateinit var fetchRepository: FetchRepository
    private lateinit var fetchService: FetchService
    private lateinit var logger: Logger

    @Before
    fun setup() {
        // Mock FetchService using mockk
        fetchService = mockk()
        logger = mockk(relaxed = true)

        // Create the FetchRepository with the mocked FetchService
        fetchRepository = FetchRepository(fetchService, logger)
    }

    @Test
    fun `test fetchHiring success with non-empty response`() = runTest {
        // Arrange: Create a list of Hiring objects to be returned by the mock
        val hiringList = listOf(
            Hiring(id = 1, listId = 1, name = "Item 1"),
            Hiring(id = 2, listId = 2, name = "Item 2")
        )

        // Mock the fetchHiring method of FetchService to return the hiringList
        coEvery { fetchService.fetchHiring() } returns hiringList

        // Act: Call fetchHiring
        val result = fetchRepository.fetchHiring()

        // Assert: Verify that the result is Success and contains the hiringList
        assertTrue(result is FetchRepository.Result.Success)
        assertEquals((result as FetchRepository.Result.Success).data, hiringList)
    }

    @Test
    fun `test fetchHiring success with empty response`() = runTest {
        // Arrange: Mock an empty list response
        coEvery { fetchService.fetchHiring() } returns emptyList()

        // Act: Call fetchHiring
        val result = fetchRepository.fetchHiring()

        // Assert: Verify that the result is Empty
        assertTrue(result is FetchRepository.Result.Empty)
    }

    @Test
    fun `test fetchHiring error scenario`() = runTest {
        val exception = Exception("Network Error")
        coEvery { fetchService.fetchHiring() } throws exception

        val result = fetchRepository.fetchHiring()

        assertTrue(result is FetchRepository.Result.Error)
        assertEquals((result as FetchRepository.Result.Error).exception.message, exception.message)
    }


    @Test
    fun `test fetchHiring logs errors when network fails`() = runTest {
        val exception = Exception("Network Error")
        coEvery { fetchService.fetchHiring() } throws exception

        fetchRepository.fetchHiring()

        // Verify that the logger's e method was called with the exception
        verify { logger.e("FetchRepository", "Network Error: ${exception.message}", exception) }
    }
}