package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IService
import de.markus.learning.domain.util.Validator
import io.quarkus.panache.common.Page
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

interface ILessonService : IService<LessonDTO>


class LessonValidator : Validator<ILessonDTO> {
    override fun isValid(obj: ILessonDTO): Boolean {
        return true
    }
}

@ApplicationScoped
class LessonService() : AbstractService<Lesson, ILessonDTO>() {

    @Inject
    override lateinit var mapper: LessonMapper

    @Inject
    override lateinit var repository: LessonRepository

    @Inject
    override lateinit var validator: LessonValidator

}


fun LessonService.findByQuery(name: String, index: Int, pageSize: Int): Array<LessonDTO> {
    val page = Page.of(index, pageSize)

    return repository.findByQuery(name)
            .page<Lesson>(page)
            .list<Lesson>()
            .map(mapper::convertModelToDTO)
            .map{ it as LessonDTO}
            .toTypedArray()
}
