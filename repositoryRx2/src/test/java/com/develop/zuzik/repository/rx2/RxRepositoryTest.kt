package com.develop.zuzik.repository.rx2

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException
import org.junit.Test
import org.mockito.Mockito

/**
 * User: zuzik
 * Date: 2/5/17
 */
class RxRepositoryTest {

    val mockRepository: Repository<String, Int> = Mockito.mock(Repository::class.java) as Repository<String, Int>
    val rxRepository = RxRepository(mockRepository)

    @Test
    fun createSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.create("entityToCreate")).thenReturn("createdEntity")

        rxRepository
                .create("entityToCreate")
                .test()
                .assertResult("createdEntity")
    }

    @Test
    fun createSendsOnErrorEventWhenCreateThrowsException() {
        Mockito.`when`(mockRepository.create("entityToCreate")).thenThrow(CreateEntityException())

        rxRepository
                .create("entityToCreate")
                .test()
                .assertError(CreateEntityException::class.java)
    }

    @Test
    fun readWithKeySendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.readWithKey(1)).thenReturn("readEntity")

        rxRepository
                .readWithKey(1)
                .test()
                .assertResult("readEntity")
    }

    @Test
    fun readWithKeySendsOnErrorEventWhenReadWithKeyThrowsException() {
        Mockito.`when`(mockRepository.readWithKey(1)).thenThrow(ReadEntityException())

        rxRepository
                .readWithKey(1)
                .test()
                .assertError(ReadEntityException::class.java)
    }

    @Test
    fun readWithPredicateSendsOnNextAndOnCompletedEvents() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.readWithPredicate(predicate)).thenReturn(listOf("readEntity1", "readEntity2"))

        rxRepository
                .readWithPredicate(predicate)
                .test()
                .assertResult(listOf("readEntity1", "readEntity2"))
    }

    @Test
    fun readWithPredicateSendsOnErrorEventWhenReadWithPredicateThrowsException() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.readWithPredicate(predicate)).thenThrow(ReadEntityException())

        rxRepository
                .readWithPredicate(predicate)
                .test()
                .assertError(ReadEntityException::class.java)
    }

    @Test
    fun readAllSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.readAll()).thenReturn(listOf("readEntity1", "readEntity2"))

        rxRepository
                .readAll()
                .test()
                .assertResult(listOf("readEntity1", "readEntity2"))
    }

    @Test
    fun readAllSendsOnErrorEventWhenReadAllThrowsException() {
        Mockito.`when`(mockRepository.readAll()).thenThrow(ReadEntityException())

        rxRepository
                .readAll()
                .test()
                .assertError(ReadEntityException::class.java)
    }

    @Test
    fun updateSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.update("entityToUpdate")).thenReturn("updatedEntity")

        rxRepository
                .update("entityToUpdate")
                .test()
                .assertResult("updatedEntity")
    }

    @Test
    fun updateSendsOnErrorEventWhenUpdateThrowsException() {
        Mockito.`when`(mockRepository.update("entityToUpdate")).thenThrow(UpdateEntityException())

        rxRepository
                .update("entityToUpdate")
                .test()
                .assertError(UpdateEntityException::class.java)
    }

    @Test
    fun deleteSendsOnNextAndOnCompletedEvents() {
        rxRepository
                .delete("entityToDelete")
                .test()
                .assertComplete()
    }

    @Test
    fun deleteSendsOnErrorEventWhenDeleteThrowsException() {
        Mockito.`when`(mockRepository.delete("entityToDelete")).thenThrow(DeleteEntityException())

        rxRepository
                .delete("entityToDelete")
                .test()
                .assertError(DeleteEntityException::class.java)
    }

    @Test
    fun deleteWithKeySendsOnNextAndOnCompletedEvents() {
        rxRepository
                .deleteWithKey(1)
                .test()
                .assertComplete()
    }

    @Test
    fun deleteWithKeySendsOnErrorEventWhenDeleteWithKeyThrowsException() {
        Mockito.`when`(mockRepository.deleteWithKey(1)).thenThrow(DeleteEntityException())

        rxRepository
                .deleteWithKey(1)
                .test()
                .assertError(DeleteEntityException::class.java)
    }

    @Test
    fun deleteWithPredicateSendsOnNextAndOnCompletedEvents() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>

        rxRepository
                .deleteWithPredicate(predicate)
                .test()
                .assertComplete()
    }

    @Test
    fun deleteWithPredicateSendsOnErrorEventWhenDeleteWithPredicateThrowsException() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.deleteWithPredicate(predicate)).thenThrow(DeleteEntityException())

        rxRepository
                .deleteWithPredicate(predicate)
                .test()
                .assertError(DeleteEntityException::class.java)
    }
}