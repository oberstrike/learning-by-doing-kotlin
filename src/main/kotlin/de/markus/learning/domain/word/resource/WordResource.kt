package de.markus.learning.domain.word.resource

import com.maju.openapi.annotations.OASPath
import com.maju.openapi.annotations.OASResource
import com.maju.openapi.codegen.RequestMethod
import de.markus.learning.domain.word.WordDTO
import de.markus.learning.domain.word.WordService
import de.markus.learning.domain.word.findByQuery
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@ApplicationScoped
@OASResource(path = "/api/word", tagName = "Word", tagDescription = "The path to manage the word resource")
class WordResource(private val wordService: WordService) : IWordResource {

    @OASPath
    override fun getWordsByQuery(
        @QueryParam("id") id: String?,
        @QueryParam("text") text: String?,
        @QueryParam("page") page: Int?,
        @QueryParam("pageSize") pageSize: Int?
    ): List<WordDTO> {
        return wordService.findByQuery(id ?: "", text ?: "", page ?: 0, pageSize ?: 10)
    }

    @OASPath(path = "/id/{id}")
    override fun getWordById(@PathParam("id") id: String?): WordDTO {
        if (id == null)
            throw MissingParameterException("No parameter 'Id' was given.")

        return wordService.findById(id) ?: throw NotFoundException("There was no word with the id: $id found")
    }

    @OASPath(
        requestMethod = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON
    )
    override fun addWord(wordDTO: WordDTO): WordDTO {
        return wordService.save(wordDTO)
    }

    @OASPath(requestMethod = RequestMethod.DELETE)
    override fun deleteWord(wordDTO: WordDTO) {
        val result = wordService.delete(wordDTO)
        if (!result) throw BadRequestException("There was an error!!")
    }

    @OASPath(requestMethod = RequestMethod.PUT)
    override fun putWord(wordDTO: WordDTO): WordDTO {
        return wordService.put(wordDTO) ?: throw BadRequestException("There was a error while updating the word")
    }

}

//Exceptions
class MissingParameterException(msg: String = "") : BadRequestException(msg)
