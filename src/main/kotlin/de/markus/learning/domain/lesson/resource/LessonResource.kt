package de.markus.learning.domain.lesson.resource

import de.markus.learning.domain.lesson.ILessonDTO
import de.markus.learning.domain.lesson.LessonDTO
import de.markus.learning.domain.lesson.LessonService
import de.markus.learning.domain.lesson.findByQuery
import de.markus.learning.domain.word.resource.MissingParameterException
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@ApplicationScoped
class LessonResource(
        private val lessonService: LessonService
) : ILessonResource {

    override fun getLessonById(@PathParam("id") id: String?): LessonDTO {
        if (id == null) throw BadRequestException()
        val result = lessonService.findById(id)
        return when (result.isLeft) {
            true -> result.left().get() as LessonDTO
            false -> throw NotFoundException(result.right().get())
        }
    }

    override fun getLessonByQuery(name: String?, page: Int?, pageSize: Int?): Array<LessonDTO> {
        return lessonService.findByQuery(name ?: "", page ?: 0, pageSize ?: 10)
    }

    override fun addLesson(lessonDTO: LessonDTO): LessonDTO {
        val result = lessonService.save(lessonDTO)
        if (result.isRight)
            throw BadRequestException(result.right().get())
        return result.left().get() as LessonDTO
    }

    override fun deleteLesson(lessonDTO: LessonDTO?): Response {
        if (lessonDTO == null) throw MissingParameterException()
        val exception = lessonService.delete(lessonDTO)
        if (exception != null) throw exception
        return Response.ok().build()
    }

    override fun putLesson(lessonDTO: LessonDTO?): LessonDTO {
        if (lessonDTO == null) throw MissingParameterException()
        val result = lessonService.put(lessonDTO)

        return when (result.isLeft) {
            true -> result.left().get() as LessonDTO
            false -> throw BadRequestException(result.right().get())
        }
    }

}