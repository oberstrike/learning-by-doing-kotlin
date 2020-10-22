package de.markus.learning.domain.lesson

import com.ibm.asyncutil.util.Either
import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.util.IService
import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.panache.common.Page
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

interface ILessonService : IService<LessonDTO>

@ApplicationScoped
class LessonService() : AbstractService<Lesson, LessonDTO>() {

    @Inject
    override lateinit var mapper: LessonMapper

    @Inject
    override lateinit var repository: LessonRepository

}
