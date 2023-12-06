package geo

import kollections.List
import kollections.iEmptyList
import kollections.toIList
import symphony.BaseField
import symphony.TextField
import symphony.Visibilities

abstract class AbstractAddressManager : AddressManager {
    override fun entries(country: Country?): List<BaseField<String>> {
        if (country == null) return iEmptyList()
        return labels(country).map { TextField(name = it) }.toIList()
    }
}