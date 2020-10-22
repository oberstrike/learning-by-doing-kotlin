package de.markus.learning.domain.word

import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

class WordMapperTest {

    @Test
    fun convertToDTOTest() {
        val word = Word(
                text = "libertad",
                type = WordType.NOUN,
                translations = listOf("Freiheit")
        ).apply {
            id = ObjectId.get()
        }

        val mapper = WordMapper()

        val dto = mapper.convertModelToDTO(word)

        assertWordEqualWordDTO(word, dto)
    }

    @Test
    private fun assertWordEqualWordDTO(word: Word, dto: IWordDTO) {
        assert(word.text == dto.text)
        assert(word.type == dto.type)
        assert(word.translations.size == dto.translations.size)
        assert(word.id.toString() == dto.id)

        for (i in dto.translations.indices) {
            assert(dto.translations[i] == word.translations[i])
        }
    }

    @Test
    fun convertToModelTest() {
        val dto = WordDTO(
                id = ObjectId.get().toString(),
                text = "libertad",
                type = WordType.NOUN,
                translations = arrayOf("Freiheit")
        )

        val mapper = WordMapper()

        val word = mapper.convertDTOToModel(dto)

        assertWordEqualWordDTO(word, dto)
    }

}