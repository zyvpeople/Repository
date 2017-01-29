package com.develop.zuzik.repository.asserts

import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import org.junit.Assert

/**
 * User: zuzik
 * Date: 1/12/17
 */
class RepositoryAssert<Entity, Key>(private val strategy: RepositoryAssertStrategy<Entity, Key>) : Assert() {

    fun assertRepositoryBehaviour() {
        testCreate()
        testReadWithKey()
        testReadWithPredicate()
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

    /*

    @Throws(ReadEntityException::class)
    fun readAll(): List<Entity>

    @Throws(UpdateEntityException::class)
    fun update(entity: Entity): Entity

    @Throws(DeleteEntityException::class)
    fun delete(entity: Entity)

    @Throws(DeleteEntityException::class)
    fun deleteWithKey(key: Key)

    //TODO: investigate if subclasses should use concrete predicate in method's parameter
    @Throws(DeleteEntityException::class)
    fun deleteWithPredicate(predicate: Predicate<Entity>)
     */
}