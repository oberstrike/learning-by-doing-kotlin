package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.util.convertDTOsToModels
import de.markus.learning.domain.util.convertModelsToDTOs
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordMapper
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LessonMapper(
        private val wordMapper: WordMapper
) : IMapper<Lesson, ILessonDTO> {

    override fun convertDTOToModel(dto: ILessonDTO): Lesson {
        return Lesson(
                name = dto.name
        ).apply {
            if (dto.id != null) id = ObjectId(dto.id)
            if (dto.words.isNotEmpty()) words = wordMapper.convertDTOsToModels(dto.words.toList()).toMutableList()
        }
    }

    override fun convertModelToDTO(model: Lesson): ILessonDTO {
        return LessonDTO(
                id = model.id.toString(),
                name = model.name,
                words = if (model.words.isNotEmpty()) wordMapper.convertModelsToDTOs(model.words).map { it as WordDTO } else emptyList()
        )
    }

}