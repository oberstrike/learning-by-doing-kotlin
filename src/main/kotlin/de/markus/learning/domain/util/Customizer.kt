package de.markus.learning.domain.util

import de.markus.learning.domain.lesson.ILessonDTO
import de.markus.learning.domain.lesson.LessonDTO
import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordType
import io.quarkus.jsonb.JsonbConfigCustomizer
import java.lang.reflect.Type
import javax.inject.Singleton
import javax.json.JsonObject
import javax.json.bind.JsonbConfig
import javax.json.bind.serializer.DeserializationContext
import javax.json.bind.serializer.JsonbDeserializer
import javax.json.stream.JsonParser


@Singleton
class JsonBConfigCustomizer : JsonbConfigCustomizer {
    override fun customize(config: JsonbConfig) {
        config.withDeserializers(WordDTOJsonDeserializer())
        config.withDeserializers(LessonDTOJsonDeserializer())
    }
}


class LessonDTOJsonDeserializer : JsonbDeserializer<ILessonDTO>, AbstractJsonDeserializer() {
    override fun deserialize(parser: JsonParser, ctx: DeserializationContext, p2: Type?): ILessonDTO {
        val jsonValue = parser.value
        val jsonObject = jsonValue.asJsonObject()

        val id = jsonObject.getString("id", null)
        val name = jsonObject.getString("name", "")

        val jsonWords = jsonObject.getJsonArray("words")
        val words = mutableListOf<IWordDTO>()

        if (jsonWords != null) {
            val size = jsonWords.size
            for (i in 0 until size) {
                val word = extractWordDTO(jsonWords.getJsonObject(i))
                words.add(word)
            }
        }

        return LessonDTO(
                id = id,
                name = name,
                words = words
        )
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
                translations = translations.toTypedArray()

        )
    }
}

class WordDTOJsonDeserializer : JsonbDeserializer<IWordDTO>, AbstractJsonDeserializer() {
    override fun deserialize(parser: JsonParser, ctx: DeserializationContext, p2: Type): IWordDTO {

        val jsonValue = parser.value
        val jsonObject = jsonValue.asJsonObject()

        return extractWordDTO(jsonObject)
    }

}