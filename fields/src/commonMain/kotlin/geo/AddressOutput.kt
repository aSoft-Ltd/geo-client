package geo

import kase.Result
import kase.catching
import kollections.List
import neat.required
import symphony.BaseField

data class AddressOutput(
    var country: Country?,
    var entries: List<BaseField<String>>
) {
    val full
        get() = listOf(Entry(label = "Country", value = country?.label)) + entries.map {
            Entry(label = it.label.text, value = it.output)
        }

    fun toDto(): Result<AddressDto> = catching {
        AddressDto(
            country = ::country.required,
            entries = entries.map { Entry(label = it.label.text, value = it.output) }
        )
    }
}