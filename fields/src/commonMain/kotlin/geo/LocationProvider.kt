package geo

interface LocationProvider {
    val name: String
    fun transform(input: String?): GeoLocation?
}