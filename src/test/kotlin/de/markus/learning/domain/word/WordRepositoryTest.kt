package de.markus.learning.domain.word

import de.markus.learning.domain.lesson.Lesson
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WordRepositoryTest {

    @Inject
    lateinit var wordRepository: WordRepository

    @BeforeAll
    fun prepare() {
        println("prepare")
    }

    @Test
    fun reset() {
        wordRepository.deleteAll()
    }


    @Test
    fun test() {
        reset()

        val word = Word().apply {
            text = "Hello"
            translations = listOf("Hallo")
        }

        if (!wordRepository.find("text", "Hello").firstResultOptional<Word>().isPresent)
            wordRepository.persist(word)


        val result = wordRepository.findByQuery(text = "Hello").firstResult<Word>()
        val id = result.id

        val result2 = wordRepository.findByQuery(id = id.toString()).firstResult<Word>()
        val result3 = wordRepository.findByQuery(text = "Welt").firstResultOptional<Word>()

        assert(result.text == word.text)
        assert(result2.text == word.text)
        assert(result3.isEmpty)

        reset()
    }

    @Test
    fun relationTest() {
        reset()

        val word = Word()
        word.persist()

        val lesson = Lesson()
        lesson.words = listOf(word)

        lesson.persist()


        val all = wordRepository.findAll().list<Word>()

        println(all)

    }
}