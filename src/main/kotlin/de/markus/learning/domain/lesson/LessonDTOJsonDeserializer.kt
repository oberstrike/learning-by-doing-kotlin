package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.AbstractJsonDeserializer
import de.markus.learning.domain.word.IWordDTO
import java.lang.reflect.Type
import javax.json.bind.serializer.DeserializationContext
import javax.json.bind.serializer.JsonbDeserializer
import javax.json.stream.JsonParser


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
                words = words.toTypedArray()
        )
    }
}
