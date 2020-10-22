package de.markus.learning.domain.util

import com.ibm.asyncutil.util.Either
import io.quarkus.mongodb.panache.PanacheMongoEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

interface IService<T> {
    fun save(dto: T): Either<T, String>
    fun delete(dto: T): Exception?
    fun findAll(index: Int, size: Int): List<T>
    fun findById(id: String): Either<T, String>
}


abstract class AbstractService<T : PanacheMongoEntity, U>: IService<U> {

    abstract val mapper: IMapper<T, U>
    abstract val repository: PanacheMongoRepository<T>

    override fun save(dto: U): Either<U, String> {
        val word = mapper.convertDTOToModel(dto)
        return try {
            repository.persist(word)
            Either.left(mapper.convertModelToDTO(word))
        } catch (e: Exception) {
            Either.right("There was an error while saving")
        }
    }

    override fun delete(dto: U): Exception? {
        val model = mapper.convertDTOToModel(dto)
        val original = repository.findByIdOptional(model.id)
        if (original.isEmpty) return NotFoundException("No word with the id ${model.id} was found")

        return try {
            repository.delete(model)
            null
        } catch (e: Exception) {
            BadRequestException("There was an error while deleting - please ask the Owner of the library")
        }
    }

    override fun findAll(index: Int, size: Int): List<U> {
        val page = Page.of(index, size)
        return repository.findAll()
                .page<T>(page)
                .list<T>()
                .map(mapper::convertModelToDTO)
    }

    override fun findById(id: String): Either<U, String> {
        val optional = repository
                .findByIdOptional(ObjectId(id))
        return if (optional.isPresent)
            Either.left(optional.get().let { mapper.convertModelToDTO(it) })
        else
            Either.right("No Word with id $id was found")
    }


}