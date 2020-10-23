package de.markus.learning.domain.word.resource

import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordService
import de.markus.learning.domain.word.findByQuery
import javax.validation.Valid
import javax.ws.rs.*


class WordResource(private val wordService: WordService) : IWordResource {

    override fun getWordsByQuery(
            @QueryParam("id") id: String?,
            @QueryParam("text") text: String?,
            @QueryParam("page") page: Int?,
            @QueryParam("pageSize") pageSize: Int?
    ): Array<WordDTO> {
        return wordService.findByQuery(id?: "", text?: "", page?: 0, pageSize?: 10)
    }

    override fun getWordById(@PathParam("id") id: String?): WordDTO {
        if (id == null)
            throw MissingParameterException("No parameter 'Id' was given.")
        val result = wordService.findById(id)
        if (result.isRight)
            throw NotFoundException(result.right().get())

        return result.left().get() as WordDTO
    }

    override fun addWord(wordDTO: WordDTO): WordDTO {
        val result = wordService.save(wordDTO)
        if (result.isRight)
            throw BadRequestException(result.right().get())
        return result.left().get() as WordDTO
    }

    override fun deleteWord(wordDTO: WordDTO) {
        val result = wordService.delete(wordDTO)
        if (result != null) throw result
    }

    override fun putWord(wordDTO: WordDTO) {
        wordService.put(wordDTO)
    }

}

//Exceptions
class MissingParameterException(msg: String = "") : BadRequestException(msg)