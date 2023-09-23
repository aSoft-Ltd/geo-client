package geo

import geo.internal.AddressFieldImpl
import neat.ValidationFactory
import symphony.Changer
import symphony.Fields
import symphony.Visibility
import kotlin.reflect.KMutableProperty0

fun AddressField(
    name: KMutableProperty0<AddressOutput?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = "Address",
    onChange: Changer<AddressOutput>? = null,
    factory: ValidationFactory<AddressOutput>? = null
): AddressField = AddressFieldImpl(name, label, visibility, hint, onChange, factory)

fun Fields<Any>.address(
    name: KMutableProperty0<AddressOutput?>,
    label: String = name.name,
    visibility: Visibility = Visibility.Visible,
    hint: String = label,
    onChange: Changer<AddressOutput>? = null,
    factory: ValidationFactory<AddressOutput>? = null
): AddressField = getOrCreate(property = name) {
    AddressFieldImpl(name, label, visibility, hint, onChange, factory)
}