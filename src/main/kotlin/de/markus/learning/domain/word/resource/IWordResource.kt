package de.markus.learning.domain.word.resource

import de.markus.learning.domain.util.ResponseError
import de.markus.learning.domain.word.IWordDTO
import de.markus.learning.domain.word.WordDTO
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters
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
    @Parameters
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "default",
                        description = "unexpected Error",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),

                APIResponse(
                        responseCode = "200",
                        description = "Returns a list of words",
                        content = arrayOf(
                                Content(
                                        mediaType = MediaType.APPLICATION_JSON,
                                        schema = Schema(type = SchemaType.ARRAY, implementation = IWordDTO::class)
                                )
                        )
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error while adding a word",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "401",
                        description = "You are not authorized",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "403",
                        description = "You have no rights to see this content",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "429",
                        description = "Too many requests",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                )
            ]
    )
    fun getWordsByQuery(
            @QueryParam("id") @Parameter(name = "id", required = false, schema = Schema(
                    implementation = String::class,
                    maxLength = 24,
                    minLength = 24,
                    pattern = "^[a-zA-Z0-9]*\$"
            )) id: String?,
            @QueryParam("text") @Parameter(name = "text", required = false, schema = Schema(
                    implementation = String::class,
                    maxLength = 140,
                    minLength = 0,
                    pattern = "^[a-zA-Z0-9]*\$"
            )) text: String?,
            @QueryParam("page") @Parameter(name = "page", required = false, schema = Schema(
                    implementation = Int::class,
                    minimum = "0",
                    maximum = "1000"
            )) page: Int?,
            @QueryParam("pageSize") @Parameter(name = "pageSize", required = false, schema = Schema(
                    implementation = Int::class,
                    minimum = "1",
                    maximum = "1000"
            )) pageSize: Int?
    ): Array<IWordDTO>

    @GET
    @Path("/id/{id}")
    @Produces("application/json")
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "default",
                        description = "unexpected Error",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),

                APIResponse(
                        responseCode = "200",
                        description = "Returns a word depending on its ID",
                        content = arrayOf(
                                Content(
                                        mediaType = MediaType.APPLICATION_JSON,
                                        schema = Schema(implementation = IWordDTO::class)
                                )
                        )
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error while searching a word",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "401",
                        description = "You are not authorized",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "403",
                        description = "You have no rights to see this content",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "429",
                        description = "Too many requests",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                )
            ]
    )
    fun getWordById(@PathParam("id") @Parameter(name = "id", required = false, schema = Schema(
            implementation = String::class,
            maxLength = 24,
            minLength = 24,
            pattern = "^[a-zA-Z0-9]*\$"
    )) id: String?): IWordDTO

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "default",
                        description = "unexpected Error",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),

                APIResponse(
                        responseCode = "200",
                        description = "A new word was successfully created",
                        content = arrayOf(
                                Content(
                                        mediaType = MediaType.APPLICATION_JSON,
                                        schema = Schema(implementation = IWordDTO::class)
                                )
                        )
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error while adding a word",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "401",
                        description = "You are not authorized",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "403",
                        description = "You have no rights to see this content",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "415",
                        description = "Not supported Media",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "429",
                        description = "Too many requests",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                )
            ]
    )
    fun addWord(wordDTO: IWordDTO): IWordDTO

    @DELETE
    @Consumes("application/json")
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "default",
                        description = "unexpected Error",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "204",
                        description = "The word is deleted"
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error while deleting the word",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "404",
                        description = "The word was not found",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "401",
                        description = "You are not authorized",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "403",
                        description = "You have no rights to see this content",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "415",
                        description = "Not supported Media",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "429",
                        description = "Too many requests",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                )
            ]
    )
    fun deleteWord(wordDTO: IWordDTO)

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @APIResponses(
            value = [
                APIResponse(
                        responseCode = "default",
                        description = "unexpected Error",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),

                APIResponse(
                        responseCode = "204",
                        description = "The word is updated"
                ),
                APIResponse(
                        responseCode = "400",
                        description = "There was an error while editing the word",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "404",
                        description = "The word was not found",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "401",
                        description = "You are not authorized",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "403",
                        description = "You have no rights to see this content",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "415",
                        description = "Not supported Media",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                ),
                APIResponse(
                        responseCode = "429",
                        description = "Too many requests",
                        content = [
                            Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = Schema(implementation = ResponseError::class)
                            )
                        ]
                )
            ]
    )
    fun putWord(wordDTO: IWordDTO): IWordDTO
}
