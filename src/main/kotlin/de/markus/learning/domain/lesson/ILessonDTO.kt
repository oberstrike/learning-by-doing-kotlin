package de.markus.learning.domain.lesson

import de.markus.learning.domain.word.IWordDTO

interface ILessonDTO {
    val id: String?
    val name: String
    val words: Array<IWordDTO>
}

data class LessonDTO(
        override val id: String?,
        override val name: String,
        override val words: Array<IWordDTO>
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
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + words.contentHashCode()
        return result
    }
}