package de.markus.learning.domain.lesson.resource

import com.maju.openapi.annotations.OASPath
import com.maju.openapi.annotations.OASResource
import com.maju.openapi.codegen.RequestMethod
import de.markus.learning.domain.lesson.LessonDTO
import de.markus.learning.domain.lesson.ILessonService
import de.markus.learning.domain.word.IWordService
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.resource.MissingParameterException
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@ApplicationScoped
@OASResource(path = "/api/lesson", tagName = "Lesson", tagDescription = "The path to manage the lesson resource")
class LessonResource(
    private val lessonService: ILessonService,
    private val wordService: IWordService
) : ILessonResource{

    @OASPath(path = "/id/{id}")
    override fun byId(@PathParam("id") id: String?): LessonDTO {
        if (id == null) throw BadRequestException()
        val result = lessonService.findById(id)
        return when (result != null) {
            true -> result
            false -> throw NotFoundException("There was no lesson with the id: $id found")
        }
    }

    @OASPath
    override fun getLessonByQuery(name: String?, page: Int?, pageSize: Int?): Array<LessonDTO> {
        return lessonService.findByQuery(name ?: "", page ?: 0, pageSize ?: 10)
    }

    @OASPath(requestMethod = RequestMethod.POST)
    override fun addLesson(lessonDTO: LessonDTO): LessonDTO {
        return lessonService.save(lessonDTO)
    }

    @OASPath(requestMethod = RequestMethod.DELETE)
    override fun deleteLesson(lessonDTO: LessonDTO?): Response {
        if (lessonDTO == null) throw MissingParameterException()
        val successfullyDeleted = lessonService.delete(lessonDTO)
        if (!successfullyDeleted) throw BadRequestException("There was an error while deleting this leesson")
        return Response.ok().build()
    }

    @OASPath(requestMethod = RequestMethod.PUT)
    override fun putLesson(lessonDTO: LessonDTO?): LessonDTO {
        if (lessonDTO == null) throw MissingParameterException()

        val words = lessonDTO.words
        val newWords = mutableListOf<WordDTO>()

        if (words.isNotEmpty()) {
            for (wordDTO in words) {
                if (wordDTO.id != null) {
                    val dto = wordService.findById(wordDTO.id!!)
                    if (dto != null)
                        newWords.add(dto)
                }

            }
        }
        lessonDTO.words = newWords

        val result = lessonService.put(lessonDTO)

        return when (result != null) {
            true -> result
            false -> throw BadRequestException("There was an error while updating this lesson")
        }
    }

}
