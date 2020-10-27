package de.markus.learning.domain.word

import de.markus.learning.domain.util.MongoTestResource
import de.markus.learning.domain.util.getRandomWord
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.*
import javax.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTestResource(MongoTestResource::class)
class WordServiceTest {

    @Inject
    lateinit var wordService: WordService

    lateinit var wordDTO: IWordDTO

    private val dto = getRandomWord()

    @BeforeAll
    fun prepare() {
        wordDTO = wordService.save(dto)
    }

    @AfterAll
    fun teardown() {
        val dto = wordDTO.id?.let { wordService.findById(it) }
        dto?.let { wordService.delete(it) }
    }

    @Test
    fun queryTest() {
        val result = wordService.findByQuery(id = "", text = dto.text, size = 10, index = 0)
        Assertions.assertEquals(1, result.size)
    }

    @Test
    fun queryTest_negative() {
        val result = wordService.findByQuery(id = "", text = "xxxxx", size = 10, index = 0)
        Assertions.assertEquals(0, result.size)
    }

    @Test
    fun findAllTest() {
        val result = wordService.findByQuery()
        Assertions.assertEquals(1, result.size)
    }

}