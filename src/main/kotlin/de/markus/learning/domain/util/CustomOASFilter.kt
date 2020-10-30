package de.markus.learning.domain.util

import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.models.media.Schema


class CustomOASFilter: OASFilter {


    private val baseStringPattern: String = "^[a-zA-Z0-9 ]*\$"
    private val baseMinLength: String = "0"
    private val baseMaxLength: String = "100"


    override fun filterSchema(schema: Schema?): Schema {
        if (schema != null)
            when (schema.type) {
                Schema.SchemaType.OBJECT -> schema.additionalPropertiesBoolean = false
                Schema.SchemaType.STRING -> if (schema.pattern == null) {
                    schema.pattern = baseStringPattern
                    schema.minLength = baseMinLength.toIntOrNull() ?: 0
                    schema.maxLength = baseMaxLength.toIntOrNull() ?: 100
                }

                else -> Unit
            }

        return super.filterSchema(schema)
    }
}
