package de.markus.learning.domain.util

import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordType
import io.github.serpro69.kfaker.Faker
import org.bson.types.ObjectId

val faker = Faker()

fun getRandomWord(hasId: Boolean = false): IWordDTO {

    val text = faker.animal.name()
    val translations = arrayOf(faker.animal.name())
    return WordDTO(
            text = text,
            type = WordType.values().random(),
            translations = translations
    ).apply { if (hasId) id = ObjectId().toString() }
}

fun getRandomWords(count: Int) = (0..count).map { getRandomWord() }
