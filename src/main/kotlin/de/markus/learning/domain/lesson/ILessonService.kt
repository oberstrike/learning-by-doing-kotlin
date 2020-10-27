package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.util.IService
import de.markus.learning.domain.util.Validator
import de.markus.learning.domain.word.IWord
import de.markus.learning.domain.word.Word
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordService
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException

interface ILessonService : IService<ILessonDTO> {
    fun findByQuery(name: String, index: Int = 0, pageSize: Int = 10): Array<ILessonDTO>

    fun addWordToLesson(lessonDTO: ILessonDTO, word: IWord): ILessonDTO

    fun removeWordFromLesson(lessonDTO: ILessonDTO, word: IWord): ILessonDTO
}

interface ILessonValidator : Validator<ILessonDTO>

@ApplicationScoped
class LessonValidator : ILessonValidator {
    override fun isValid(obj: ILessonDTO): Boolean {
        return true
    }
}

@ApplicationScoped
class LessonService(
        override val mapper: IMapper<Lesson, ILessonDTO>,
        override val repository: LessonRepository,
        override val validator: ILessonValidator
) : AbstractService<Lesson, ILessonDTO>(), ILessonService {

    override fun findByQuery(name: String, index: Int, pageSize: Int): Array<ILessonDTO> {
        val page = Page.of(index, pageSize)

        return repository.findByQuery(name)
                .page<Lesson>(page)
                .list<Lesson>()
                .map(mapper::convertModelToDTO)
                .toTypedArray()
    }


    override fun addWordToLesson(lessonDTO: ILessonDTO, word: IWord): ILessonDTO {
        if (lessonDTO.id == null) throw BadRequestException("No Id given")
        val objectId = ObjectId(lessonDTO.id)
        val oLesson = repository.findByIdOptional(objectId)
        if (oLesson.isEmpty) throw NotFoundException("There was no lesson with the id ${lessonDTO.id} found")
        val lesson = oLesson.get()

        lesson.words.add(word as Word)
        repository.persistOrUpdate(lesson)
        return repository.findById(objectId).let { mapper.convertModelToDTO(it) }
    }

    override fun removeWordFromLesson(lessonDTO: ILessonDTO, word: IWord): ILessonDTO {
        val objectId = ObjectId(lessonDTO.id)
        val oLesson = repository.findByIdOptional(objectId)
        if (oLesson.isEmpty) throw NotFoundException("There was no lesson with the id ${lessonDTO.id} found")
        val lesson = oLesson.get()

        lesson.words.remove(word)
        repository.persistOrUpdate(lesson)
        return repository.findById(objectId).let { mapper.convertModelToDTO(it) }
    }


}




