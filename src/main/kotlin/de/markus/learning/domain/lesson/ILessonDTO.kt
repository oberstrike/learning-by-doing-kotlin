package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.Indexable
import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "lesson")
interface ILessonDTO : Indexable {

    @get:Schema(maxLength = 24)
    override val id: String?

    @get:Schema(required = true, maxLength = 24)
    val name: String

    @get:Schema(maxItems = 100)
    var words: List<IWordDTO>
}

data class LessonDTO(
        override var id: String? = null,
        override var name: String = "",
        override var words: List<IWordDTO> = emptyList()
) : ILessonDTO