package de.markus.learning.domain.word.resource

import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordService
import de.markus.learning.domain.word.findByQuery
import javax.annotation.Resource
import javax.ws.rs.*


class WordResource(private val wordService: WordService) : IWordResource {

    override fun getWordsByQuery(
            @QueryParam("id") id: String?,
            @QueryParam("text") text: String?,
            @QueryParam("page") page: Int?,
            @QueryParam("pageSize") pageSize: Int?
    ): Array<IWordDTO> {
        return wordService.findByQuery(id ?: "", text ?: "", page ?: 0, pageSize ?: 10)
    }

    override fun getWordById(@PathParam("id") id: String?): IWordDTO {
        if (id == null)
            throw MissingParameterException("No parameter 'Id' was given.")
        val result = wordService.findById(id) ?: throw NotFoundException("There was no word with the id: $id found")

        return result as WordDTO
    }

    override fun addWord(wordDTO: IWordDTO): IWordDTO {
        val result = wordService.save(wordDTO)
        return result as WordDTO
    }

    override fun deleteWord(wordDTO: IWordDTO) {
        val result = wordService.delete(wordDTO)
        if (!result) throw BadRequestException("There was an error!!")
    }

    override fun putWord(wordDTO: IWordDTO) {
        wordService.put(wordDTO)
    }

}

//Exceptions
class MissingParameterException(msg: String = "") : BadRequestException(msg)