package de.markus.learning.domain.lesson

import de.markus.learning.domain.word.Word
import io.quarkus.mongodb.panache.MongoEntity
import io.quarkus.mongodb.panache.PanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonProperty

interface ILesson {
    var name: String
    var words: List<Word>
}

@MongoEntity(collection = "lesson")
data class Lesson(
        override var name: String = "",
        @BsonProperty
        override var words: List<Word> = emptyList()
) : ILesson, PanacheMongoEntity()