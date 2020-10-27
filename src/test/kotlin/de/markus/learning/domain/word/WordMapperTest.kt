package de.markus.learning.domain.word

import de.markus.learning.domain.util.getRandomWord
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WordMapperTest {

    @Test
    fun convertDTOToModelAndModelToDTOTest() {
        val mapper = WordMapper()

        val wordDTO = getRandomWord(true)

        val wordModel = mapper.convertDTOToModel(wordDTO)
        assertWordEqualWordDTO(wordModel, wordDTO)
        val newWordDTO = mapper.convertModelToDTO(wordModel)
        Assertions.assertEquals(wordDTO, newWordDTO)
    }


    @Test
    fun convertToDTOTest() {
        val text = "libertad"
        val wordType = WordType.NOUN
        val translations = listOf("Freiheit")
        val word = Word(
                text = text,
                type = wordType,
                translations = translations
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