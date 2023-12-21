package geo.transformers

import geo.AddressDto
import geo.AddressOutput
import kollections.emptyList
import kollections.map
import symphony.TextField

fun AddressDto?.toOutput() = AddressOutput(
    country = this?.country,
    entries = this?.entries?.map {
        TextField(name = it.label, value = it.value)
    } ?: emptyList()
)