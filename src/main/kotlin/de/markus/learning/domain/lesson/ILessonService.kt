package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.util.IService
import de.markus.learning.domain.util.Validator
import de.markus.learning.domain.word.IWord
import de.markus.learning.domain.word.Word
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

interface ILessonService : IService<LessonDTO> {
    fun findByQuery(name: String, index: Int = 0, pageSize: Int = 10): Array<LessonDTO>

    fun addWordToLesson(lessonDTO: LessonDTO, word: IWord): LessonDTO

    fun removeWordFromLesson(lessonDTO: LessonDTO, word: IWord): LessonDTO
}

interface ILessonValidator : Validator<LessonDTO>

@ApplicationScoped
class LessonValidator : ILessonValidator {
    override fun isValid(obj: LessonDTO): Boolean {
        return true
    }
}

@ApplicationScoped
class LessonService(
    override val mapper: IMapper<Lesson, LessonDTO>,
    override val repository: LessonRepository,
    override val validator: ILessonValidator
) : AbstractService<Lesson, LessonDTO>(), ILessonService {

    override fun findByQuery(name: String, index: Int, pageSize: Int): Array<LessonDTO> {
        val page = Page.of(index, pageSize)

        return repository.findByQuery(name)
                .page<Lesson>(page)
                .list<Lesson>()
                .map(mapper::convertModelToDTO)
                .toTypedArray()
    }


    override fun addWordToLesson(lessonDTO: LessonDTO, word: IWord): LessonDTO {
        if (lessonDTO.id == null) throw BadRequestException("No Id given")
        val objectId = ObjectId(lessonDTO.id)
        val oLesson = repository.findByIdOptional(objectId)
        if (oLesson.isEmpty) throw NotFoundException("There was no lesson with the id ${lessonDTO.id} found")
        val lesson = oLesson.get()

        lesson.words.add(word as Word)
        repository.persistOrUpdate(lesson)
        return repository.findById(objectId).let { mapper.convertModelToDTO(it) }
    }

    override fun removeWordFromLesson(lessonDTO: LessonDTO, word: IWord): LessonDTO {
        val objectId = ObjectId(lessonDTO.id)
        val oLesson = repository.findByIdOptional(objectId)
        if (oLesson.isEmpty) throw NotFoundException("There was no lesson with the id ${lessonDTO.id} found")
        val lesson = oLesson.get()

        lesson.words.remove(word)
        repository.persistOrUpdate(lesson)
        return repository.findById(objectId).let { mapper.convertModelToDTO(it) }
    }


}




