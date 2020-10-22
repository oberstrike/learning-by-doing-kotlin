package de.markus.learning.domain.lesson.resource

import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.tags.Tags
import javax.ws.rs.Path

@Path("/api/lesson")
@Tags(
        Tag(name = "Lesson", description = "The path to manage the lesson resource")
)
interface ILessonResource {



}