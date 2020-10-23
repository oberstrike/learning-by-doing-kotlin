package de.markus.learning.domain.word

import de.markus.learning.domain.util.Indexable
import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size
import kotlin.math.min

@Schema(name = "Word")
interface IWordDTO : Indexable {
    @get:Schema(required = false, maxLength = 24, pattern = "^[a-zA-Z0-9]*\$")
    override val id: String?

    @get:Schema(required = true, maxLength = 24, pattern = "^[a-zA-Z0-9]*\$")
    val text: String

    @get:Schema(required = true, maxLength = 12)
    val type: WordType

    @get:Schema(required = true, maxLength = 24, maxItems = 50, minItems = 1)
    val translations: Array<String>
}

data class WordDTO(
        override val id: String? = "",
        @Size(message = "", min = 1, max = 24)
        override val text: String = "",
        override val type: WordType = WordType.NOUN,
        override val translations: Array<String> = emptyArray()
) : IWordDTO {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordDTO

        if (id != other.id) return false
        if (text != other.text) return false
        if (type != other.type) return false
        if (!translations.contentEquals(other.translations)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + translations.contentHashCode()
        return result
    }
}