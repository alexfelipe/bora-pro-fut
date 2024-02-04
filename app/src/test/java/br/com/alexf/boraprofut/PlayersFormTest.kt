package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.playersForm.duplicateNames
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldNotContain
import org.junit.Assert.assertEquals
import org.junit.Test

class PlayersFormTest {

    @Test
    fun shouldReturnDuplicatesNamesGivenAString(){
        val namesWithDuplicates = """
            a
            b
            c
            a
            b
        """.trimIndent()
        val duplicatesName = namesWithDuplicates.duplicateNames()
        println(duplicatesName)
        duplicatesName shouldContainAll setOf("a", "b")
    }

}