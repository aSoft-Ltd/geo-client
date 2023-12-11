@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package geo

import symphony.TransformingField
import kotlinx.JsExport

interface LocationField : TransformingField<String, GeoLocation> {
    val provider: LocationProvider
}