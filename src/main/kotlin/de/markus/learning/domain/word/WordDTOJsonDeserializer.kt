package de.markus.learning.domain.word

import de.markus.learning.domain.util.AbstractJsonDeserializer
import java.lang.reflect.Type
import javax.json.bind.serializer.DeserializationContext
import javax.json.bind.serializer.JsonbDeserializer
import javax.json.stream.JsonParser

class WordDTOJsonDeserializer : JsonbDeserializer<IWordDTO>, AbstractJsonDeserializer() {
    override fun deserialize(parser: JsonParser, ctx: DeserializationContext, p2: Type): IWordDTO {

        val jsonValue = parser.value
        val jsonObject = jsonValue.asJsonObject()

        return extractWordDTO(jsonObject)
    }

}
