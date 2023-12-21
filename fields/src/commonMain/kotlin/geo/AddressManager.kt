package geo

import symphony.BaseField
import kollections.List

interface AddressManager {

    fun entries(country: Country?): List<BaseField<String>>

    fun labels(country: Country) : List<String>
}