package de.markus.learning.domain.util

import de.markus.learning.domain.lesson.LessonDTOJsonDeserializer
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordDTOJsonDeserializer
import de.markus.learning.domain.word.WordType
import io.quarkus.jsonb.JsonbConfigCustomizer
import javax.inject.Singleton
import javax.json.JsonObject
import javax.json.bind.JsonbConfig

val wordDeserializer = WordDTOJsonDeserializer()
val lessonDeserializer = LessonDTOJsonDeserializer()

@Singleton
class JsonBConfigCustomizer : JsonbConfigCustomizer {
    override fun customize(config: JsonbConfig) {
        config.withDeserializers(wordDeserializer)
        config.withDeserializers(lessonDeserializer)
    }
}


open class AbstractJsonDeserializer {
    protected fun extractWordDTO(jsonObject: JsonObject): WordDTO {
        val id = jsonObject.getString("id", null)

        val textJson = jsonObject.getString("text", "")

        val typeJson = jsonObject.getString("type", WordType.NOUN.toString())
        val translations = mutableListOf<String>()
        val translationsJson = jsonObject.getJsonArray("translations")

        if (translationsJson != null) {
            val size = translationsJson.size
            for (i in 0 until size) {
                translations.add(translationsJson.getString(i))
            }
        }


        return WordDTO(
                id = id,
                text = textJson,
                type = WordType.valueOf(typeJson),
                translations = translations

        )
    }
}

