package de.markus.learning.domain.lesson

import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.mongodb.panache.PanacheQuery
import javax.enterprise.context.ApplicationScoped

interface ILessonRepository : PanacheMongoRepository<Lesson> {
    fun findByQuery(name: String): PanacheQuery<Lesson>
}

@ApplicationScoped
class LessonRepository : ILessonRepository {

    override fun findByQuery(name: String): PanacheQuery<Lesson> {
        return find("name like ?1", name)
    }
}