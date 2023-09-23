@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package geo

import symphony.TransformingField
import kotlin.js.JsExport

interface LocationField : TransformingField<String, GeoLocation> {
    val provider: LocationProvider
}