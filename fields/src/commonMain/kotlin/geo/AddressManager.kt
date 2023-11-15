package geo

import symphony.BaseField

interface AddressManager {

    fun entries(country: Country?): List<BaseField<String>>

    fun labels(country: Country) : List<String>
}