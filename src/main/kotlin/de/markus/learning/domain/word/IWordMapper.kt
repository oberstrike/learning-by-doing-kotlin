package de.markus.learning.domain.word

import de.markus.learning.domain.util.IMapper
import org.bson.types.ObjectId
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.factory.Mappers
import javax.enterprise.context.ApplicationScoped

@Mapper(uses = [AfterWordMapper::class], componentModel = "cdi")
interface WordMapper : IMapper<Word, WordDTO> {

    companion object {
        val INSTANCE: WordMapper = Mappers.getMapper(WordMapper::class.java)
    }

    @Mapping(target = "id", ignore = true)
    override fun convertModelToDTO(model: Word): WordDTO

    @Mapping(target = "id", ignore = true)
    override fun convertDTOToModel(dto: WordDTO): Word

}

@ApplicationScoped
class AfterWordMapper {

    @AfterMapping
    fun toModel(@MappingTarget model: Word, dto: WordDTO) {
        if (dto.id != null) {
            model.id = ObjectId(dto.id)
        }
    }

    @AfterMapping
    fun toDTO(model: Word, @MappingTarget dto: WordDTO){
        if(model.id != null){
            dto.id = model.id.toHexString()
        }
    }

}
