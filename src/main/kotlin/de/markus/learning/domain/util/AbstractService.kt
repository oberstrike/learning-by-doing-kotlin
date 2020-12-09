package de.markus.learning.domain.util



import de.markus.learning.domain.word.WordDTO
import io.quarkus.mongodb.panache.PanacheMongoEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

interface IService<T> {
    fun save(dto: T): T
    fun delete(dto: T): Boolean
    fun findAll(index: Int = 0, size: Int = 10): List<T>
    fun findById(id: String): T?
    fun put(dto: T): T?
}

interface Validator<T> {
    fun isValid(obj: T): Boolean
}

@ApplicationScoped
class WordValidator : Validator<WordDTO> {
    override fun isValid(obj: WordDTO): Boolean {
        return true
    }
}

abstract class AbstractService<T : PanacheMongoEntity, U : Indexed> : IService<U> {

    abstract val mapper: IMapper<T, U>
    abstract val repository: PanacheMongoRepository<T>
    abstract val validator: Validator<U>

    private fun <S> whenValid(dto: U, block: (dto: U) -> S): S {
        if (validator.isValid(dto)) return block.invoke(dto)
        throw BadRequestException("The DTO is not a valid object.")
    }

    override fun save(dto: U): U {
        if (dto.id != null || dto.id == "") dto.id = null

        val word = mapper.convertDTOToModel(dto)

        return try {
            repository.persist(word)
            mapper.convertModelToDTO(word)
        } catch (e: Exception) {
            e.printStackTrace()
            throw BadRequestException("There was an error while saving")
        }
    }

    @Throws(NotFoundException::class)
    override fun delete(dto: U): Boolean = whenValid(dto) {
        val model = mapper.convertDTOToModel(dto)
        val original = repository.findByIdOptional(model.id)
        if (original.isEmpty) throw NotFoundException("No word with the id ${model.id} was found")

        return@whenValid try {
            repository.delete(model)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun findAll(index: Int, size: Int): List<U> {
        val page = Page.of(index, size)
        return repository.findAll()
                .page<T>(page)
                .list<T>()
                .map(mapper::convertModelToDTO)
    }

    override fun findById(id: String): U? {
        if (!ObjectId.isValid(id))
            throw BadRequestException("The id is not valid.")
        return repository.findByIdOptional(ObjectId(id)).map { mapper.convertModelToDTO(it) }.orElse(null)
    }

    override fun put(dto: U): U? = whenValid(dto) {
        if (!ObjectId.isValid(dto.id)) return@whenValid null
        val model = mapper.convertDTOToModel(dto)
        repository.persistOrUpdate(model)

        return@whenValid mapper.convertModelToDTO(model)
    }

}
