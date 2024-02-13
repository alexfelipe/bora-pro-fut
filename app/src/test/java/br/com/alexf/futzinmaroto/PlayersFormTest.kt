package br.com.alexf.futzinmaroto

import br.com.alexf.futzinmaroto.features.playersForm.duplicateNames
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContainAll
import org.junit.Test

class PlayersFormTest {

    @Test
    fun shouldReturnDuplicatesNamesGivenAString() {
        val namesWithDuplicates = """
            a
            b
            c
            a
            b
        """.trimIndent()
        val namesWithoutDuplicates = """
            a
            b
            c
            d
            ab
            """.trimIndent()
        val duplicatesName = namesWithDuplicates.duplicateNames()
        duplicatesName shouldContainAll setOf("a", "b")
        val noDuplicateNames = namesWithoutDuplicates.duplicateNames()
        noDuplicateNames.shouldBeEmpty()
    }

}