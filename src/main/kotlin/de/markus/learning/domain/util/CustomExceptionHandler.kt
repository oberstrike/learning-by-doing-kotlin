package de.markus.learning.domain.util

import io.quarkus.resteasy.runtime.NotFoundExceptionMapper
import io.quarkus.security.UnauthorizedException
import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.ws.rs.BadRequestException
import javax.ws.rs.ForbiddenException
import javax.ws.rs.NotFoundException
import javax.ws.rs.NotSupportedException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class CustomNotFoundExceptionMapper : NotFoundExceptionMapper() {

    private val statusCode = 404

    override fun toResponse(exception: NotFoundException): Response {
        return Response.status(statusCode).entity(ResponseError(statusCode, exception.message ?: "")).build()
    }
}

@Provider
class CustomExceptionHandler : ExceptionMapper<RuntimeException> {
    override fun toResponse(exception: RuntimeException): Response {
        val statusCode = when (exception) {
            is BadRequestException -> 400
            is UnauthorizedException -> 401
            is ForbiddenException -> 403
            is NotSupportedException -> 415
            else -> 400
        }

        val error = ResponseError(
                code = statusCode,
                message = exception.message ?: ""
        )

        return Response.status(statusCode).entity(error).build()
    }
}

@Schema(name = "Error", requiredProperties = ["code", "message"], example = "{\n" + "  \"code\": 400,\n" + "  \"message\": \"Here is an error message\"\n" + "}")
data class ResponseError(
        @get:Schema(name = "code", minimum = "400", maximum = "500")
        val code: Int = 0,
        @get:Schema(minLength = 0, maxLength = 140, pattern = "^[a-zA-Z0-9 ]*\$")
        val message: String = ""
)

