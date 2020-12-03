package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.word.WordMapper
import org.bson.types.ObjectId
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ApplicationPath

@Mapper(uses = [AfterLessonMapper::class, WordMapper::class], componentModel = "cdi")
interface LessonMapper: IMapper<Lesson, LessonDTO>{

    @Mapping(target = "id", ignore = true)
    override fun convertDTOToModel(dto: LessonDTO): Lesson

    @Mapping(target = "id", ignore = true)
    override fun convertModelToDTO(model: Lesson): LessonDTO

}


@ApplicationScoped
class AfterLessonMapper{

    @AfterMapping
    fun toModel(@MappingTarget model: Lesson, dto: LessonDTO) {
        if (dto.id != null) {
            model.id = ObjectId(dto.id)
        }
    }

    @AfterMapping
    fun toDTO(model: Lesson, @MappingTarget dto: LessonDTO){
        if(model.id != null){
            dto.id = model.id.toHexString()
        }
    }

}
