package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.Indexable
import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "Lesson")
interface ILessonDTO : Indexable {

    @get:Schema(maxLength = 24)
    override var id: String?

    @get:Schema(required = true, maxLength = 24)
    val name: String

    @get:Schema(maxItems = 100)
    var words: Array<IWordDTO>
}

data class LessonDTO(
        override var id: String? = null,
        override var name: String = "",
        override var words: Array<IWordDTO> = emptyArray()
) : ILessonDTO {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LessonDTO

        if (id != other.id) return false
        if (name != other.name) return false
        if (!words.contentEquals(other.words)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + words.contentHashCode()
        return result
    }
}