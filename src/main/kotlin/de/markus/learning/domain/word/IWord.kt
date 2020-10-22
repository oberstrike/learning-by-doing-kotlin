package de.markus.learning.domain.word

import io.quarkus.mongodb.panache.MongoEntity
import io.quarkus.mongodb.panache.PanacheMongoEntity

interface IWord {
    var text: String
    var type: WordType
    var translations: List<String>
}

@MongoEntity(collection = "word")
data class Word(
        override var text: String = "",
        override var type: WordType = WordType.NOUN,
        override var translations: List<String> = emptyList()
) : PanacheMongoEntity(), IWord


enum class WordType {
    VERB, NOUN, ADVERB, ADJECTIVE, DETERMINER, PRONOUN, CONJUNCTION, PREPOSITION
}
