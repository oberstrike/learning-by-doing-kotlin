package de.markus.learning.domain.util

import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.models.media.Schema


abstract class AbstractOASFilter : OASFilter, IFilterSchema {

    interface IOnFilterSchemaHandler {
        fun onFilterSchema(schema: Schema)
    }

    override val onFilterSchemaHandlers: List<IOnFilterSchemaHandler> = emptyList()


    override fun filterSchema(schema: Schema): Schema {
        onFilterSchemaHandlers.onEach { it.onFilterSchema(schema) }
        return super.filterSchema(schema)
    }
}

interface IFilterSchema {
    val onFilterSchemaHandlers: List<AbstractOASFilter.IOnFilterSchemaHandler>
}
