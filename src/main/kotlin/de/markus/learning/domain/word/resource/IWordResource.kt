package de.markus.learning.domain.word.resource

import de.markus.learning.domain.word.IWordDTO
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.tags.Tags
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * @author Markus Jürgens
 * @since 21.10.2020
 */



@Path("/api/word")
@Tags(
        Tag(name = "Word", description = "The path to manage the word resource")
)
interface IWordResource {

    @GET
    @Produces("application/json")
    fun getWordsByQueue(
            @QueryParam("id") id: String?,
            @QueryParam("text") text: String?,
            @QueryParam("page") page: Int?,
            @QueryParam("pageSize") pageSize: Int?
    ): Array<IWordDTO>

    @GET
    @Path("/id/{id}")
    @Produces("application/json")
    fun getWordById(@PathParam("id") id: String?): IWordDTO

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "201",
                        description = "A new game was successfully created",
                        content = arrayOf(
                                Content(
                                        mediaType = MediaType.APPLICATION_JSON,
                                        schema = Schema(implementation = IWordDTO::class)
                                )
                        )
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error when creating a new game"
                )
            ]
    )
    fun addWord(wordDTO: IWordDTO): IWordDTO

    @DELETE
    fun deleteWord(wordDTO: IWordDTO)
}