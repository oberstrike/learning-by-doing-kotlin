package de.markus.learning.domain.lesson

import de.markus.learning.domain.util.MongoTestResource
import de.markus.learning.domain.util.getRandomWord
import de.markus.learning.domain.word.WordRepository
import de.markus.learning.domain.word.WordService
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(MongoTestResource::class)
class LessonServiceTest {

    @Inject
    lateinit var lessonRepository: LessonRepository

    @Inject
    lateinit var lessonValidator: ILessonValidator

    @Inject
    lateinit var mapper: LessonMapper

    @Inject
    lateinit var wordRepository: WordRepository

    @Inject
    lateinit var wordService: WordService


    @Test
    fun saveTest() {
        //Init class to test
        val lessonService = LessonService(mapper, lessonRepository, lessonValidator)
        val lessonDTO = LessonDTO(null, "libertad", emptyList())
        val result = lessonService.save(lessonDTO)

        var arrayOfLessonDTOs = lessonService.findByQuery(name = "libertad")
        assert(arrayOfLessonDTOs.isNotEmpty())

        lessonService.delete(result)


        arrayOfLessonDTOs = lessonService.findByQuery(name = "libertad")
        assert(arrayOfLessonDTOs.isEmpty())
    }

    @Test
    fun addWordToLessonTest() {

        //Init class to test
        val lessonService = LessonService(mapper, lessonRepository, lessonValidator)

        //Init needed objects for the test
        val wordDTO = getRandomWord()
        val savedWordDTO = wordService.save(wordDTO)
        val word = wordService.mapper.convertDTOToModel(savedWordDTO)

        val lessonDTO = LessonDTO(name = "Sprache", words = emptyList(), id = null)
        val savedLesson = lessonService.save(lessonDTO)
        //--

        val resultWordDTO = lessonService.addWordToLesson(savedLesson, word)
        assert(resultWordDTO.words.isNotEmpty())
        Assertions.assertNotNull(savedLesson.id)

        val findResult = lessonService.findById(savedLesson.id!!)
        assert(findResult != null)
        assert(findResult!!.words.isNotEmpty())

    }


    @Test
    fun removeWordFromLessonTest() {

        //Init class to test
        val lessonService = LessonService(mapper, lessonRepository, lessonValidator)
        val wordDTO = getRandomWord()
        val savedWordDTO = wordService.save(wordDTO)
        val word = wordService.mapper.convertDTOToModel(savedWordDTO)

        val lessonDTO = LessonDTO(name = "Sprache", words = emptyList(), id = null)
        val savedLesson = lessonService.save(lessonDTO)
        //--

        var resultWordDTO = lessonService.addWordToLesson(savedLesson, word)
        assert(resultWordDTO.words.isNotEmpty())
        Assertions.assertNotNull(savedLesson.id)

        resultWordDTO = lessonService.removeWordFromLesson(savedLesson, word)
        assert(resultWordDTO.words.isEmpty())
        Assertions.assertNotNull(savedLesson.id)

        val findResult = lessonService.findById(savedLesson.id!!)
        assert(findResult != null)
        assert(findResult!!.words.isEmpty())
    }

}