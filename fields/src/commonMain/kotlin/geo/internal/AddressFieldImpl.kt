package geo.internal

import cinematic.mutableLiveOf
import geo.AddressField
import geo.AddressManager
import geo.AddressOutput
import geo.AddressPresenter
import geo.Country
import geo.matches
import geo.transformers.toOutput
import kollections.emptyList
import kollections.toList
import kollections.find
import kollections.isNotEmpty
import kollections.onEach
import neat.ValidationFactory
import neat.Validity
import neat.custom
import neat.required
import symphony.BaseField
import symphony.Changer
import symphony.Feedbacks
import symphony.Label
import symphony.Option
import symphony.SingleChoiceField
import symphony.Visibility
import symphony.internal.AbstractHideable
import symphony.internal.BaseFieldImplState
import symphony.internal.FieldBacker
import symphony.toErrors
import symphony.toWarnings

@PublishedApi
internal class AddressFieldImpl(
    private val backer: FieldBacker<AddressOutput>,
    private val manager: AddressManager,
    private val value: AddressPresenter?,
    label: String,
    visibility: Visibility,
    hint: String,
    private val onChange: Changer<AddressOutput>?,
    factory: ValidationFactory<AddressOutput>?
) : AbstractHideable(), AddressField {

    override val country by lazy {
        SingleChoiceField(
            name = "country",
            label = "Select Country",
            items = Country.entries.toList(),
            mapper = { Option(label = it.label, value = it.name) },
            filter = { country, key -> country.matches(key) },
            value = value?.country ?: backer.asProp?.get()?.country,
            onChange = { country ->
                val out = state.value.output ?: AddressOutput(null, emptyList())
                val entries = manager.entries(country).onEach { it.smoothUpdate() }
                set(out.copy(country = country, entries = entries))
            }
        )
    }

    private fun BaseField<String>.smoothUpdate() {
        val existing = this@AddressFieldImpl.state.value.output?.entries?.find { it.label.text == label.text }?.output
        if (!existing.isNullOrBlank()) return set(existing)

        val default = value?.entries?.find { it.label == label.text }?.value
        if (!default.isNullOrBlank()) return set(default)

        val prop = backer.asProp?.get()?.entries?.find { it.label.text == label.text }?.output
        if (!prop.isNullOrBlank()) return set(prop)
    }

    override val entries get() = state.value.output?.entries ?: emptyList()

    protected val validator = custom<AddressOutput>(label).configure(factory)

    override fun set(value: AddressOutput?) {
        val res = validator.validate(value)
        val output = res.value
        backer.asProp?.set(output)
        state.value = state.value.copy(
            output = output,
            feedbacks = Feedbacks(res.toWarnings())
        )
        onChange?.invoke(output)
    }

    private val initial = BaseFieldImplState(
        name = backer.name,
        label = Label(label, this.validator.required),
        hint = hint,
        required = this.validator.required,
        output = value?.src?.toOutput() ?: backer.asProp?.get(),
        visibility = visibility,
        feedbacks = Feedbacks(emptyList()),
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