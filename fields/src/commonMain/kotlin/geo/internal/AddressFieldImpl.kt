package geo.internal

import cinematic.mutableLiveOf
import geo.AddressField
import geo.AddressManager
import geo.AddressOutput
import geo.Country
import geo.matches
import kollections.iEmptyList
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.SingleChoiceField
import symphony.Visibility
import symphony.internal.AbstractHideable
import symphony.internal.BaseFieldImpl
import symphony.toErrors
import symphony.toWarnings
import kotlin.reflect.KMutableProperty0

@PublishedApi
internal class AddressFieldImpl(
    private val property: KMutableProperty0<AddressOutput?>,
    label: String,
    visibility: Visibility,
    hint: String,
    private val onChange: Changer<AddressOutput>?,
    factory: ValidationFactory<AddressOutput>?
) : AbstractHideable(), AddressField {

    private var _country = property.get()?.country

    private var manager = AddressManager()

    override val country by lazy {
        SingleChoiceField(
            name = ::_country,
            items = Country.values().toList(),
            mapper = { Option(label = it.label, value = it.name) },
            filter = { country, key -> country.matches(key) },
            onChange = {
                val out = state.value.output ?: AddressOutput(null, iEmptyList())
                set(out.copy(country = it, entries = manager.entries(it)))
            }
        )
    }

    override val entries get() = state.value.output?.entries ?: iEmptyList()

    protected val validator = custom<AddressOutput>(label).configure(factory)

    override fun set(value: AddressOutput?) {
        val res = validator.validate(value)
        val output = res.value
        property.set(output)
        state.value = state.value.copy(
            output = property.get(),
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(property.get())
    }

    private val initial = BaseFieldImpl.State(
        name = property.name,
        label = Label(label, this.validator.required),
        hint = hint,
        required = this.validator.required,
        output = property.get(),
        visibility = visibility,
        feedbacks = Feedbacks(iEmptyList()),
    )

    override val state = mutableLiveOf(initial)

    override fun validate() = validator.validate(output)

    override fun validateToErrors(): Validity<AddressOutput> {
        val res = validator.validate(output)
        val errors = res.toErrors()
        if (errors.isNotEmpty()) {
            state.value = state.value.copy(feedbacks = Feedbacks(errors))
        }
        return res
    }

    override fun setVisibility(v: Visibility) {
        state.value = state.value.copy(visibility = v)
    }

    override fun clear() = set(null)

    override fun finish() {
        state.stopAll()
        state.history.clear()
    }

    override fun reset() {
        state.value = initial
    }

    override val name get() = state.value.name
    override val label get() = state.value.label
    override val hint get() = state.value.hint
    override val output get() = state.value.output
    override val required get() = state.value.required
    override val visibility get() = state.value.visibility
    override val feedbacks get() = state.value.feedbacks
}