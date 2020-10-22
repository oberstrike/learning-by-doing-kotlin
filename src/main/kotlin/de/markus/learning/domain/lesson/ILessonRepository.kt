package de.markus.learning.domain.lesson

import io.quarkus.mongodb.panache.PanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

interface ILessonRepository : PanacheMongoRepository<Lesson>

@ApplicationScoped
class LessonRepository : ILessonRepository