package de.markus.learning.domain.util

import arrow.core.right
import com.ibm.asyncutil.util.Either.right
import com.ibm.asyncutil.util.Either.left
import com.ibm.asyncutil.util.Either
import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.Word
import de.markus.learning.domain.word.WordDTO
import io.quarkus.mongodb.panache.PanacheMongoEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

interface IService<T> {
    fun save(dto: T): Either<T, String>
    fun delete(dto: T): Exception?
    fun findAll(index: Int, size: Int): List<T>
    fun findById(id: String): Either<T, String>
    fun put(dto: T): Either<T, String>
}

interface Validator<T> {
    fun isValid(obj: T): Boolean
}

class WordValidator : Validator<IWordDTO> {
    override fun isValid(obj: IWordDTO): Boolean {
        return true
    }
}

abstract class AbstractService<T : PanacheMongoEntity, U : Indexable> : IService<U> {

    abstract val mapper: IMapper<T, U>
    abstract val repository: PanacheMongoRepository<T>
    abstract val validator: Validator<U>

    private fun <S> whenValid(dto: U, block: (dto: U) -> S): S {
        return block.invoke(dto)
    }

    override fun save(dto: U): Either<U, String> = whenValid(dto) {
        val word = mapper.convertDTOToModel(dto)
        try {
            repository.persist(word)
            left(mapper.convertModelToDTO(word))
        } catch (e: Exception) {
            right("There was an error while saving")
        }
    }

    override fun delete(dto: U): Exception? = whenValid(dto) {
        val model = mapper.convertDTOToModel(dto)
        val original = repository.findByIdOptional(model.id)
        if (original.isEmpty) return@whenValid NotFoundException("No word with the id ${model.id} was found")

        return@whenValid try {
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
        if (!ObjectId.isValid(id))
            return right("The id is not valid.")

        val optional = repository.findByIdOptional(ObjectId(id))
        return if (optional.isPresent)
            left(optional.get().let { mapper.convertModelToDTO(it) })
        else
            right("No Word with id $id was found")
    }

    override fun put(dto: U): Either<U, String> = whenValid(dto) {

        if (!ObjectId.isValid(dto.id)) return@whenValid right("The id is not valid.")

        val model = mapper.convertDTOToModel(dto)
        repository.persistOrUpdate(model)

        return@whenValid left(mapper.convertModelToDTO(model))
    }

}