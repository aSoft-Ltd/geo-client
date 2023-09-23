@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package geo

import kollections.List
import symphony.BaseField
import symphony.SingleChoiceField
import kotlin.js.JsExport

interface AddressField : BaseField<AddressOutput> {
    val country: SingleChoiceField<Country>
    val entries: List<BaseField<String>>
}