package geo

class LameAddressManager : AbstractAddressManager() {

    override fun labels(country: Country) = when (country) {
        Country.GB -> listOf(post.town, post.code, street.name)
        Country.TZ -> listOf(address[1], address[2], district, region, box)
        Country.US -> listOf(street.name, house.number, apartmentOrSuiteOrRoom.number, zip.code)
        Country.ZA -> listOf(address[1], address[2], suburb, province, city, postal.code)
        else -> listOf(address[1], address[2], zip.code)
    }

    private companion object {

        class Address {
            operator fun get(number: Int) = "Address line $number"
        }

        val address by lazy { Address() }

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
            val name = "Street Name"
        }

        val street by lazy { Street() }

        val region = "Region"
        val city = "City"
        val suburb = "Suburb"
        val province = "Province"
        val district = "District"

        val house by lazy { Numbered("House") }

        val box = "P.O.Box"

        val zip by lazy { Coded("Zip") }

        val postal by lazy { Coded("Postal") }

        val apartmentOrSuiteOrRoom by lazy { Numbered("Apartment/Suite/Room") }
    }
}