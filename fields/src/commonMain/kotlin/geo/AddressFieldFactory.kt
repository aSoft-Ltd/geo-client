package geo

import geo.internal.AddressFieldImpl
import neat.ValidationFactory
import symphony.Changer
import symphony.Fields
import symphony.Visibility
import kotlin.reflect.KMutableProperty0
import symphony.Visibilities
import symphony.internal.FieldBacker

fun AddressField(
    name: String = "",
    label: String = name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = "Address",
    value: AddressPresenter? = null,
    onChange: Changer<AddressOutput>? = null,
    factory: ValidationFactory<AddressOutput>? = null
): AddressField = AddressFieldImpl(FieldBacker.Name(name), LameAddressManager(), value, label, visibility, hint, onChange, factory)

fun Fields<Any>.address(
    name: KMutableProperty0<AddressOutput?>,
    label: String = name.name,
    visibility: Visibility = Visibilities.Visible,
    hint: String = label,
    value: AddressPresenter? = null,
    onChange: Changer<AddressOutput>? = null,
    factory: ValidationFactory<AddressOutput>? = null
): AddressField = getOrCreate(property = name) {
    AddressFieldImpl(FieldBacker.Prop(name), LameAddressManager(), value, label, visibility, hint, onChange, factory)
}