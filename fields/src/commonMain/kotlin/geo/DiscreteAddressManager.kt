package geo

class DiscreteAddressManager : AbstractAddressManager() {

    override fun labels(country: Country) = when (country) {
        Country.GB -> listOf(post.town, post.code, street.name)
        Country.TZ -> listOf(region, district, ward, street.orVillage, plot.number, house.number, box)
        Country.US -> listOf(street.name, house.number, apartmentOrSuiteOrRoom.number, zip.code)
        Country.ZA -> listOf(street.address, building, suburb, province, city, postal.code)
        else -> listOf(street.name, house.number, apartmentOrSuiteOrRoom.number, zip.code)
    }

    private companion object {

        open class Coded(base: String) {
            val code = "$base Code"
        }

        class Post : Coded("Post") {
            val town = "Post Town"
        }

        val post by lazy { Post() }

        open class Numbered(base: String) {
            val number = "$base Number"
        }

        class Street : Numbered("Street") {
            val address = "Address"
            val name = "Street Name"
            val orVillage = "Street or Village"
        }

        val street by lazy { Street() }

        val region = "Region"
        val city = "City"
        val suburb = "Suburb"
        val building = "Building"
        val province = "Province"
        val district = "District"
        val ward = "Ward"

        val plot by lazy { Numbered("Plot") }

        val house by lazy { Numbered("House") }

        val box = "P.O.Box"

        val zip by lazy { Coded("Zip") }

        val postal by lazy { Coded("Postal") }

        val floor by lazy { Numbered("Floor") }

        val apartmentOrSuiteOrRoom by lazy { Numbered("Apartment/Suite/Room") }
    }
}