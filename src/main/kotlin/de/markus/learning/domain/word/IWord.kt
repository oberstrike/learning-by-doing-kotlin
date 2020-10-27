package de.markus.learning.domain.word

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.markus.learning.domain.util.Indexable
import io.quarkus.mongodb.panache.MongoEntity
import io.quarkus.mongodb.panache.PanacheMongoEntity
import org.bson.types.ObjectId
import org.eclipse.microprofile.openapi.annotations.media.Schema


@MongoEntity(collection = "word")
interface IWord {
    var text: String
    var type: WordType
    var translations: List<String>
}

data class Word(
        override var text: String = "",
        override var type: WordType = WordType.NOUN,
        override var translations: List<String> = emptyList()
) : PanacheMongoEntity(), IWord {

}


enum class WordType {
    VERB, NOUN, ADVERB, ADJECTIVE, DETERMINER, PRONOUN, CONJUNCTION, PREPOSITION
}
