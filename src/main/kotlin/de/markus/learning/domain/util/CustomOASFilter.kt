package de.markus.learning.domain.util

import org.eclipse.microprofile.openapi.models.media.Schema

class CustomOnFilterSchemaHandler : AbstractOASFilter.IOnFilterSchemaHandler {
    private val baseStringPattern: String = "^[a-zA-Z0-9 ]*\$"
    private val baseMinLength: String = "0"
    private val baseMaxLength: String = "100"

    override fun onFilterSchema(schema: Schema) {
        when (schema.type) {
            Schema.SchemaType.OBJECT -> schema.additionalPropertiesBoolean = false
            Schema.SchemaType.STRING -> if (schema.pattern == null) {
                schema.pattern = baseStringPattern
                schema.minLength = baseMinLength.toIntOrNull() ?: 0
                schema.maxLength = baseMaxLength.toIntOrNull() ?: 100
            }

            else -> Unit
        }
    }
}

class CustomOASFilter : AbstractOASFilter() {
    override val onFilterSchemaHandlers: List<IOnFilterSchemaHandler> = listOf(CustomOnFilterSchemaHandler())
}
