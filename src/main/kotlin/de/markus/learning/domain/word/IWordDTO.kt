package de.markus.learning.domain.word

import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "Word")
interface IWordDTO {
    @get:Schema(required = false, maxLength = 24)
    val id: String?

    @get:Schema(required = true, maxLength = 24)
    val text: String

    @get:Schema(required = true, maxLength = 12)
    val type: WordType

    @get:Schema(required = true, maxLength = 24, maxItems = 50, minItems = 1)
    val translations: Array<String>
}


data class WordDTO(
        override val id: String?,
        override val text: String,
        override val type: WordType,
        override val translations: Array<String>
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