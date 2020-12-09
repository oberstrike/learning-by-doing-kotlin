package de.markus.learning.domain.student

import com.maju.openapi.annotations.OASParameter
import com.maju.openapi.annotations.OASPath
import com.maju.openapi.annotations.OASResource
import com.maju.openapi.codegen.RequestMethod
import com.maju.openapi.codegen.generators.annotations.schema.OASBaseSchemaEnum
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

@OASResource("/api/student", "Student")
class StudentResource : IStudentResource {


    @OASPath(requestMethod = RequestMethod.POST)
    override fun uploadFile(
        @OASParameter(baseSchema = OASBaseSchemaEnum.FILE)
        multipartFormDataInput: MultipartFormDataInput
    ) {

    }

}
