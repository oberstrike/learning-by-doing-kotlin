package de.markus.learning.domain.lesson

import com.maju.openapi.annotations.OASProperty
import com.maju.openapi.annotations.OASSchema
import com.maju.openapi.codegen.generators.annotations.schema.OASBaseSchemaEnum
import de.markus.learning.domain.util.Indexable
import de.markus.learning.domain.word.WordDTO


@OASSchema(name = "Lesson")
data class LessonDTO(
    @OASProperty(baseSchema = OASBaseSchemaEnum.STRING_WITHOUT_SPACES_LENGTH_24)
    override var id: String? = null,
    override var name: String = "",
    override var words: List<WordDTO> = emptyList()
) : Indexable, ILessonDTO
