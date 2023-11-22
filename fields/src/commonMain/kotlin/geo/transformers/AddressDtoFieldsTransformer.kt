package geo.transformers

import geo.AddressDto
import geo.AddressOutput
import kollections.iEmptyList
import kollections.toIList
import symphony.TextField

fun AddressDto?.toOutput() = AddressOutput(
    country = this?.country,
    entries = this?.entries?.map {
        TextField(
            name = it.label,
            label = it.label,
            hint = it.label
        )
    }?.toIList() ?: iEmptyList()
)