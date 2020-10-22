package de.markus.learning.domain.word

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WordServiceTest {

    @Inject
    lateinit var wordService: WordService

    @BeforeAll
    fun prepare() {
        wordService.save(WordDTO(id = null, text = "Test", type = WordType.NOUN, translations = arrayOf("Test")))
    }

    @AfterAll
    fun teardown() {
        val dto = wordService.findByQuery(id = "", text = "Test", size = 10, index = 0).first()
        wordService.delete(dto)
    }

    @Test
    fun queryTest() {
        val query = wordService.findByQuery(id = "", text = "Test", size = 10, index = 0)
        println(query.contentToString())

    }

}