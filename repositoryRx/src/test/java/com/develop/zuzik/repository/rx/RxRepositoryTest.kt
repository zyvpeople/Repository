package com.develop.zuzik.repository.rx

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException
import org.junit.Test
import org.mockito.Mockito
import rx.observers.TestSubscriber

/**
 * User: zuzik
 * Date: 2/5/17
 */
//TODO: create repositoryRx2 for Rx 2.0 support
class RxRepositoryTest {

    val mockRepository: Repository<String, Int> = Mockito.mock(Repository::class.java) as Repository<String, Int>
    val rxRepository = RxRepository(mockRepository)

    @Test
    fun createSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.create("entityToCreate")).thenReturn("createdEntity")

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.create("entityToCreate").subscribe(mockSubscriber)

        mockSubscriber.assertValues("createdEntity")
        mockSubscriber.assertCompleted()
    }

    @Test
    fun createSendsOnErrorEventWhenCreateThrowsException() {
        Mockito.`when`(mockRepository.create("entityToCreate")).thenThrow(CreateEntityException())

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.create("entityToCreate").subscribe(mockSubscriber)

        mockSubscriber.assertError(CreateEntityException::class.java)
    }

    @Test
    fun readWithKeySendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.readWithKey(1)).thenReturn("readEntity")

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.readWithKey(1).subscribe(mockSubscriber)

        mockSubscriber.assertValues("readEntity")
        mockSubscriber.assertCompleted()
    }

    @Test
    fun readWithKeySendsOnErrorEventWhenReadWithKeyThrowsException() {
        Mockito.`when`(mockRepository.readWithKey(1)).thenThrow(ReadEntityException())

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.readWithKey(1).subscribe(mockSubscriber)

        mockSubscriber.assertError(ReadEntityException::class.java)
    }

    @Test
    fun readWithPredicateSendsOnNextAndOnCompletedEvents() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.readWithPredicate(predicate)).thenReturn(listOf("readEntity1", "readEntity2"))

        val mockSubscriber = TestSubscriber<List<String>>()
        rxRepository.readWithPredicate(predicate).subscribe(mockSubscriber)

        mockSubscriber.assertValues(listOf("readEntity1", "readEntity2"))
        mockSubscriber.assertCompleted()
    }

    @Test
    fun readWithPredicateSendsOnErrorEventWhenReadWithPredicateThrowsException() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.readWithPredicate(predicate)).thenThrow(ReadEntityException())

        val mockSubscriber = TestSubscriber<List<String>>()
        rxRepository.readWithPredicate(predicate).subscribe(mockSubscriber)

        mockSubscriber.assertError(ReadEntityException::class.java)
    }

    @Test
    fun readAllSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.readAll()).thenReturn(listOf("readEntity1", "readEntity2"))

        val mockSubscriber = TestSubscriber<List<String>>()
        rxRepository.readAll().subscribe(mockSubscriber)

        mockSubscriber.assertValues(listOf("readEntity1", "readEntity2"))
        mockSubscriber.assertCompleted()
    }

    @Test
    fun readAllSendsOnErrorEventWhenReadAllThrowsException() {
        Mockito.`when`(mockRepository.readAll()).thenThrow(ReadEntityException())

        val mockSubscriber = TestSubscriber<List<String>>()
        rxRepository.readAll().subscribe(mockSubscriber)

        mockSubscriber.assertError(ReadEntityException::class.java)
    }

    @Test
    fun updateSendsOnNextAndOnCompletedEvents() {
        Mockito.`when`(mockRepository.update("entityToUpdate")).thenReturn("updatedEntity")

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.update("entityToUpdate").subscribe(mockSubscriber)

        mockSubscriber.assertValues("updatedEntity")
        mockSubscriber.assertCompleted()
    }

    @Test
    fun updateSendsOnErrorEventWhenUpdateThrowsException() {
        Mockito.`when`(mockRepository.update("entityToUpdate")).thenThrow(UpdateEntityException())

        val mockSubscriber = TestSubscriber<String>()
        rxRepository.update("entityToUpdate").subscribe(mockSubscriber)

        mockSubscriber.assertError(UpdateEntityException::class.java)
    }

    @Test
    fun deleteSendsOnNextAndOnCompletedEvents() {
        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.delete("entityToDelete").subscribe(mockSubscriber)

        mockSubscriber.assertValues(true)
        mockSubscriber.assertCompleted()
    }

    @Test
    fun deleteSendsOnErrorEventWhenDeleteThrowsException() {
        Mockito.`when`(mockRepository.delete("entityToDelete")).thenThrow(DeleteEntityException())

        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.delete("entityToDelete").subscribe(mockSubscriber)

        mockSubscriber.assertError(DeleteEntityException::class.java)
    }

    @Test
    fun deleteWithKeySendsOnNextAndOnCompletedEvents() {
        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.deleteWithKey(1).subscribe(mockSubscriber)

        mockSubscriber.assertValues(true)
        mockSubscriber.assertCompleted()
    }

    @Test
    fun deleteWithKeySendsOnErrorEventWhenDeleteWithKeyThrowsException() {
        Mockito.`when`(mockRepository.deleteWithKey(1)).thenThrow(DeleteEntityException())

        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.deleteWithKey(1).subscribe(mockSubscriber)

        mockSubscriber.assertError(DeleteEntityException::class.java)
    }

    @Test
    fun deleteWithPredicateSendsOnNextAndOnCompletedEvents() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>

        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.deleteWithPredicate(predicate).subscribe(mockSubscriber)

        mockSubscriber.assertValues(true)
        mockSubscriber.assertCompleted()
    }

    @Test
    fun deleteWithPredicateSendsOnErrorEventWhenDeleteWithPredicateThrowsException() {
        val predicate = Mockito.mock(Predicate::class.java) as Predicate<String>
        Mockito.`when`(mockRepository.deleteWithPredicate(predicate)).thenThrow(DeleteEntityException())

        val mockSubscriber = TestSubscriber<Boolean>()
        rxRepository.deleteWithPredicate(predicate).subscribe(mockSubscriber)

        mockSubscriber.assertError(DeleteEntityException::class.java)
    }
}