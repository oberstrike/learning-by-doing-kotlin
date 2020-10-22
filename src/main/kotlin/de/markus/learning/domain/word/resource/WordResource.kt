package de.markus.learning.domain.word.resource

import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordService
import de.markus.learning.domain.word.findByQuery
import javax.ws.rs.*


class WordResource(private val wordService: WordService) : IWordResource {

    override fun getWordsByQueue(
            @QueryParam("id") id: String?,
            @QueryParam("text") text: String?,
            @QueryParam("page") page: Int?,
            @QueryParam("pageSize") pageSize: Int?
    ): Array<IWordDTO> {
        return wordService.findByQuery(id?: "", text?: "", page?: 0, pageSize?: 10)
    }

    override fun getWordById(@PathParam("id") id: String?): IWordDTO {
        if (id == null)
            throw MissingParameterException("No parameter 'Id' was given.")
        val result = wordService.findById(id)
        if (result.isRight)
            throw NotFoundException(result.right().get())

        return result.left().get()
    }

    override fun addWord(wordDTO: IWordDTO): IWordDTO {
        val result = wordService.save(wordDTO)
        if (result.isRight)
            throw BadRequestException(result.right().get())
        return result.left().get() as WordDTO
    }

    override fun deleteWord(wordDTO: IWordDTO) {
        val result = wordService.delete(wordDTO)
        if (result != null) throw result
    }

}

//Exceptions
class MissingParameterException(msg: String = "") : BadRequestException(msg)