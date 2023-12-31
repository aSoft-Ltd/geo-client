@file:JsExport

package geo

import kotlinx.JsExport
import geo.internal.GooglePlacesApiParser

object GoogleLocationProvider : LocationProvider {
    override val name = "Google"
    private val parser = GooglePlacesApiParser()
    override fun transform(input: String?) = parser.parseOrNull(input)
}