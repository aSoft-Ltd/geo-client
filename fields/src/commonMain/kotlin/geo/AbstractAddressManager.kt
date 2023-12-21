package geo

import kollections.List
import kollections.emptyList
import kollections.map
import symphony.BaseField
import symphony.TextField

abstract class AbstractAddressManager : AddressManager {
    override fun entries(country: Country?): List<BaseField<String>> {
        if (country == null) return emptyList()
        return labels(country).map { TextField(name = it) }
    }
}