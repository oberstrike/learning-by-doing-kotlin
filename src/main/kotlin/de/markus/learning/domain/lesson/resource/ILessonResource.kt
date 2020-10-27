package de.markus.learning.domain.lesson.resource

import de.markus.learning.domain.lesson.ILessonDTO
import de.markus.learning.domain.lesson.LessonDTO
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.tags.Tags
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("/api/lesson")
@Tags(
        Tag(name = "Lesson", description = "The path to manage the lesson resource")
)
interface ILessonResource {

    @GET
    @Path("/id/{id}")
    @Produces("application/json")
    fun getLessonById(@PathParam("id") id: String?): ILessonDTO

    @GET
    @Produces("application/json")
    fun getLessonByQuery(@QueryParam("name") name: String?,
                         @QueryParam("page") page: Int?,
                         @QueryParam("pageSize") pageSize: Int?): Array<ILessonDTO>

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    fun addLesson(lessonDTO: ILessonDTO): ILessonDTO

    @DELETE
    @Produces("application/json")
    fun deleteLesson(lessonDTO: ILessonDTO?): Response?

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    fun putLesson(lessonDTO: ILessonDTO?): ILessonDTO


}