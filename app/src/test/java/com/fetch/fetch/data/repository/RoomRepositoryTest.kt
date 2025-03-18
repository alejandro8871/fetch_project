package com.fetch.fetch.data.repository

import com.fetch.fetch.data.local.HiringDAO
import com.fetch.fetch.data.model.Hiring
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RoomRepositoryTest {

    // Mocking the DAO class
    private lateinit var hiringDAO: HiringDAO
    private lateinit var roomRepository: RoomRepository

    // Sample data for tests
    private val sampleHiring = Hiring(id = 1, listId = 1, name = "Item 1")
    private val sampleHiringList = listOf(sampleHiring)

    @Before
    fun setup() {
        // Initialize the mock DAO
        hiringDAO = mockk()

        // Initialize RoomRepository with the mocked DAO
        roomRepository = RoomRepository(hiringDAO)
    }

    @Test
    fun testInsertAllInsertItems() = runBlocking {
        // Arrange: Mock DAO behavior
        coEvery { hiringDAO.clearFetchItems() } returns Unit
        coEvery { hiringDAO.insertFetchItems(sampleHiringList) } returns Unit

        // Act: Call insertAll on RoomRepository
        roomRepository.insertAll(sampleHiringList)

        // Assert: Verify DAO methods were called
        coVerify { hiringDAO.clearFetchItems() }
        coVerify { hiringDAO.insertFetchItems(sampleHiringList) }
    }

    @Test
    fun testCleanDatabaseCallClearFetchItems() = runBlocking {
        // Arrange: Mock DAO behavior
        coEvery { hiringDAO.clearFetchItems() } returns Unit

        // Act: Call cleanDatabase on RoomRepository
        roomRepository.cleanDatabase()

        // Assert: Verify that clearFetchItems was called
        coVerify { hiringDAO.clearFetchItems() }
    }
}