package com.develop.zuzik.repository.asserts

import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException
import org.junit.Assert

/**
 * User: zuzik
 * Date: 1/12/17
 */
//TODO: all fails should have message
//TODO: if check empty array check for size but not isEmpty
class RepositoryAssert<Entity, Key>(private val strategy: RepositoryAssertStrategy<Entity, Key>) : Assert() {

    fun assertRepositoryBehaviour() {
        testCreate()
        testReadWithKey()
        testReadWithPredicate()
        testReadAll()
        testUpdate()
        testDelete()
        testDeleteWithKey()
        testDeleteWithPredicate()
    }

    private fun execute(test: () -> Unit) {
        test()
        strategy.clearRepository()
    }

    //region create

    private fun testCreate() {
        execute { testCreateSavesEntity() }
        execute { testCreateDoesNotModifyEntityThatPassedAsArgument() }
        execute { testCreateReturnsNotSameEntity() }
        execute { testCreateSetsKeyToReturnedEntity() }
        execute { testCreateReturnsEqualEntityButWithId() }
        execute { testCreateThrowsCreateEntityExceptionWhenPassEntityWithKey() }
    }

    private fun testCreateSavesEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()

        repository.create(entity)

        val savedEntity = strategy.setKey(entity, strategy.key1())
        val readEntity = repository.readAll().first()

        assertEquals(savedEntity, readEntity)
    }

    private fun testCreateDoesNotModifyEntityThatPassedAsArgument() {
        val entity = strategy.createEntity()
        strategy.repository().create(entity)
        assertFalse(strategy.hasKey(entity))
    }

    private fun testCreateReturnsNotSameEntity() {
        val entity = strategy.createEntity()
        val createdEntity = strategy.repository().create(entity)
        assertNotSame(createdEntity, entity)
    }

    private fun testCreateSetsKeyToReturnedEntity() {
        val entity = strategy.createEntity()
        val createdEntity = strategy.repository().create(entity)
        val expectedKey = strategy.key1()
        val resultKey = strategy.getKey(createdEntity)
        assertEquals(expectedKey, resultKey)
    }

    private fun testCreateReturnsEqualEntityButWithId() {
        val entity = strategy.createEntity()
        val createdEntity = strategy.repository().create(entity)
        val expectedEntity = strategy.setKey(entity, strategy.getKey(createdEntity))
        assertEquals(expectedEntity, createdEntity)
    }

    private fun testCreateThrowsCreateEntityExceptionWhenPassEntityWithKey() {
        try {
            val entity = strategy.createEntity()
            val entityWithKey = strategy.setKey(entity, strategy.key1())
            strategy.repository().create(entityWithKey)
            fail()
        } catch (e: CreateEntityException) {
            assertTrue(true)
        }
    }

    //endregion

    //region readWithKey

    private fun testReadWithKey() {
        execute { testReadWithKeyReturnsEntityWithKey() }
        execute { testReadWithKeyReturnsCopyOfEntity() }
        execute { testReadWithKeyThrowsReadEntityExceptionWhenEntityWithKeyDoesNotExist() }
    }

    private fun testReadWithKeyReturnsEntityWithKey() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        val savedEntity = strategy.setKey(entity, strategy.key1())

        val readEntity = repository.readWithKey(strategy.key1())

        assertEquals(savedEntity, readEntity)
    }

    private fun testReadWithKeyReturnsCopyOfEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val readEntity1 = repository.readWithKey(strategy.key1())
        val readEntity2 = repository.readWithKey(strategy.key1())

        assertNotSame(readEntity1, readEntity2)
    }

    private fun testReadWithKeyThrowsReadEntityExceptionWhenEntityWithKeyDoesNotExist() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        try {
            repository.readWithKey(strategy.notExistedKey())
            fail()
        } catch (e: ReadEntityException) {
            assertTrue(true)
        }
    }

    //endregion

    //region readWithPredicate

    private fun testReadWithPredicate() {
        execute { testReadWithPredicateReturnsCorrectEntities() }
        execute { testReadWithPredicateReturnsCopyOfEntities() }
        execute { testReadWithPredicateReturnsEmptyListIfEntitiesDontExist() }
    }

    private fun testReadWithPredicateReturnsCorrectEntities() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())
        val savedEntity2 = strategy.setKey(entity, strategy.key2())
        val expectedEntities = listOf(savedEntity1)

        val readEntities = repository.readWithPredicate(strategy.entityWithKeyPredicate(strategy.key1()))

        assertEquals(expectedEntities, readEntities)
    }

    private fun testReadWithPredicateReturnsCopyOfEntities() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val readEntities1 = repository.readWithPredicate(strategy.entityWithKeyPredicate(strategy.key1()))
        val readEntities2 = repository.readWithPredicate(strategy.entityWithKeyPredicate(strategy.key1()))

        assertNotSame(readEntities1, readEntities2)
        assertNotSame(readEntities1[0], readEntities2[0])
    }

    private fun testReadWithPredicateReturnsEmptyListIfEntitiesDontExist() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val readEntities = repository.readWithPredicate(strategy.entityWithKeyPredicate(strategy.key2()))

        assertTrue(readEntities.isEmpty())
    }

    //endregion

    //region readAll

    private fun testReadAll() {
        execute { testReadAllReturnsCorrectEntities() }
        execute { testReadAllReturnsCopyOfEntities() }
        execute { testReadAllReturnsEmptyListIfEntitiesDontExist() }
    }

    private fun testReadAllReturnsCorrectEntities() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())
        val savedEntity2 = strategy.setKey(entity, strategy.key2())
        val expectedEntities = listOf(savedEntity1, savedEntity2)

        val readEntities = repository.readAll()

        assertEquals(expectedEntities, readEntities)
    }

    private fun testReadAllReturnsCopyOfEntities() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val readEntities1 = repository.readAll()
        val readEntities2 = repository.readAll()

        assertNotSame(readEntities1, readEntities2)
        assertNotSame(readEntities1[0], readEntities2[0])
    }

    private fun testReadAllReturnsEmptyListIfEntitiesDontExist() {
        val repository = strategy.repository()

        val readEntities = repository.readAll()

        assertTrue(readEntities.isEmpty())
    }

    //endregion

    //region update

    private fun testUpdate() {
        execute { testUpdateUpdatesEntity() }
        execute { testUpdateReturnsNotSameEntity() }
        execute { testUpdateReturnsEqualEntity() }
        execute { testUpdateThrowsUpdateEntityExceptionWhenPassEntityWithoutKey() }
        execute { testUpdateThrowsUpdateEntityExceptionWhenPassEntityWithNotExistedKey() }
    }

    private fun testUpdateUpdatesEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val savedEntity = strategy.setKey(entity, strategy.key1())
        val updatedEntity = strategy.updateProperties(savedEntity)

        repository.update(updatedEntity)

        val readEntity = repository.readAll().first()

        assertEquals(updatedEntity, readEntity)
    }

    private fun testUpdateReturnsNotSameEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val savedEntity = strategy.setKey(entity, strategy.key1())
        val updatedEntity = strategy.updateProperties(savedEntity)

        val resultOfUpdate = repository.update(updatedEntity)

        assertNotSame(updatedEntity, resultOfUpdate)
    }

    private fun testUpdateReturnsEqualEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)

        val savedEntity = strategy.setKey(entity, strategy.key1())
        val updatedEntity = strategy.updateProperties(savedEntity)

        val resultOfUpdate = repository.update(updatedEntity)

        assertEquals(updatedEntity, resultOfUpdate)
    }

    private fun testUpdateThrowsUpdateEntityExceptionWhenPassEntityWithoutKey() {
        try {
            val entityWithoutKey = strategy.createEntity()
            val repository = strategy.repository()
            repository.create(entityWithoutKey)

            repository.update(entityWithoutKey)
            fail()
        } catch (e: UpdateEntityException) {
            assertTrue(true)
        }
    }

    private fun testUpdateThrowsUpdateEntityExceptionWhenPassEntityWithNotExistedKey() {
        try {
            val entityWithoutKey = strategy.createEntity()
            val repository = strategy.repository()
            repository.create(entityWithoutKey)

            val notExistedEntity = strategy.setKey(entityWithoutKey, strategy.key2())

            repository.update(notExistedEntity)
            fail()
        } catch (e: UpdateEntityException) {
            assertTrue(true)
        }
    }

    //endregion

    //region delete

    private fun testDelete() {
        execute { testDeleteDeletesEntity() }
        execute { testDeleteThrowsDeleteEntityExceptionWhenEntityWithoutKey() }
        execute { testDeleteThrowsDeleteEntityExceptionWhenEntityWithKeyDoesNotExist() }
    }

    private fun testDeleteDeletesEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())
        val savedEntity2 = strategy.setKey(entity, strategy.key2())

        repository.delete(savedEntity1)

        val readEntities = repository.readAll()

        assertEquals(listOf(savedEntity2), readEntities)
    }

    private fun testDeleteThrowsDeleteEntityExceptionWhenEntityWithoutKey() {
        val entityWithoutKey = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entityWithoutKey)

        try {
            repository.delete(entityWithoutKey)
            fail()
        } catch (e: DeleteEntityException) {
            assertTrue(true)
        }
    }

    private fun testDeleteThrowsDeleteEntityExceptionWhenEntityWithKeyDoesNotExist() {
        val entityWithoutKey = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entityWithoutKey)

        val notExistedEntity = strategy.setKey(strategy.createEntity(), strategy.key2())

        try {
            repository.delete(notExistedEntity)
            fail()
        } catch (e: DeleteEntityException) {
            assertTrue(true)
        }
    }

    //endregion

    //region deleteWithKey

    private fun testDeleteWithKey() {
        execute { testDeleteWithKeyDeletesEntity() }
        execute { testDeleteWithKeyThrowsDeleteEntityExceptionWhenKeyDoesNotExist() }
    }

    private fun testDeleteWithKeyDeletesEntity() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())
        val savedEntity2 = strategy.setKey(entity, strategy.key2())

        repository.deleteWithKey(strategy.key1())

        val readEntities = repository.readAll()

        assertEquals(listOf(savedEntity2), readEntities)
    }

    private fun testDeleteWithKeyThrowsDeleteEntityExceptionWhenKeyDoesNotExist() {
        val entityWithoutKey = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entityWithoutKey)

        try {
            repository.deleteWithKey(strategy.key2())
            fail()
        } catch (e: DeleteEntityException) {
            assertTrue(true)
        }
    }

    //endregion

    //region deleteWithPredicate

    private fun testDeleteWithPredicate() {
        execute { testDeleteWithPredicateDeletesCorrectEntities() }
        execute { testDeleteWithPredicateDoNothingIfEntitiesDoNotExist() }
        execute { testReadWithPredicateReturnsEmptyListIfEntitiesDoNotExist() }
    }

    private fun testDeleteWithPredicateDeletesCorrectEntities() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())
        val savedEntity2 = strategy.setKey(entity, strategy.key2())
        val expectedEntities = listOf(savedEntity2)

        repository.deleteWithPredicate(strategy.entityWithKeyPredicate(strategy.key1()))

        val readEntities = repository.readAll()

        assertEquals(expectedEntities, readEntities)
    }

    private fun testDeleteWithPredicateDoNothingIfEntitiesDoNotExist() {
        val entity = strategy.createEntity()
        val repository = strategy.repository()
        repository.create(entity)
        val savedEntity1 = strategy.setKey(entity, strategy.key1())

        repository.deleteWithPredicate(strategy.entityWithKeyPredicate(strategy.key2()))

        val expectedEntities = listOf(savedEntity1)
        val readEntities = repository.readAll()

        assertEquals(expectedEntities, readEntities)
    }

    private fun testReadWithPredicateReturnsEmptyListIfEntitiesDoNotExist() {
        val repository = strategy.repository()

        repository.deleteWithPredicate(strategy.entityWithKeyPredicate(strategy.key1()))

        val expectedEntities = emptyList<Entity>()
        val readEntities = repository.readAll()

        assertEquals(expectedEntities, readEntities)
    }

    //endregion
}