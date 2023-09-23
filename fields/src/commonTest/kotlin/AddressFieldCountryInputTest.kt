import geo.AddressField
import geo.AddressOutput
import geo.Country
import geo.Entry
import kollections.iEmptyList
import kommander.expect
import symphony.Visibility
import kotlin.test.Test

class AddressFieldCountryInputTest {

    private var output: AddressOutput? = AddressOutput(country = null, iEmptyList())

    @Test
    fun should_be_able_to_set_a_country() {
        val field = AddressField(::output)
        field.country.select(Country.TZ)
        expect(field.output?.country).toBe(Country.TZ)
    }

    @Test
    fun should_be_able_to_set_respective_address_entries_for_tanzania() {
        val field = AddressField(::output)
        field.country.select(Country.TZ)
        expect(field.entries.map { it.label.text }).toContain("Region", "District", "Ward")
        field.entries[0].set("Dar es Salaam")
        field.entries[1].set("Kinondoni")
        expect(field.output?.full).toContain(Entry("Region", "Dar es Salaam"))
    }

    @Test
    fun should_be_able_to_hide_country_input() {
        val field = AddressField(::output)
        field.country.hide()
        expect(field.country.state.value.visibility).toBe(Visibility.Hidden)
    }

    @Test
    fun changing_the_country_should_change_the_entries() {
        val field = AddressField(::output)
        field.country.select(Country.TZ)
        expect(field.entries.map { it.label.text }).toContain("Region")
        field.country.select(Country.US)
        expect(field.entries.map { it.label.text }).toContain("Street Name")
    }
}