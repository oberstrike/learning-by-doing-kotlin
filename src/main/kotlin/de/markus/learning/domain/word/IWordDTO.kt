package de.markus.learning.domain.word

import com.maju.openapi.annotations.OASProperty
import com.maju.openapi.annotations.OASSchema
import com.maju.openapi.codegen.generators.annotations.schema.OASBaseSchemaEnum
import de.markus.learning.domain.util.Indexable
import org.eclipse.microprofile.openapi.annotations.media.Schema


@OASSchema(name = "Word")
data class WordDTO(
    @OASProperty(baseSchema = OASBaseSchemaEnum.STRING_WITHOUT_SPACES_LENGTH_24)
    override var id: String? = null,
    override var text: String = "",
    override var type: WordType = WordType.NOUN,
    override var translations: List<String> = emptyList()
) : Indexable, IWordDTO
