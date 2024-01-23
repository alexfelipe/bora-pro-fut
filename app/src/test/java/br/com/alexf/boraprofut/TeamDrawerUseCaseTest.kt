package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import org.junit.Assert
import org.junit.Test

class TeamDrawerUseCaseTest {

    @Test
    fun shouldTeamPlayersGivenAmount() {
        val teamDrawer = TeamDrawerUseCase()
        val players = generatePlayers(12)
        val teams = teamDrawer.drawRandomTeams(players, 4)
        Assert.assertEquals(teams.size, 3)
        val teamA = teams[0]
        val teamB = teams[1]
        val teamC = teams[2]
        //TODO tô em dúvida se esses assertions fazem sentido...
        teamA.forEach {
            Assert.assertFalse(teamB.contains(it))
            Assert.assertFalse(teamC.contains(it))
        }
        teamB.forEach {
            Assert.assertFalse(teamA.contains(it))
            Assert.assertFalse(teamC.contains(it))
        }
        teamC.forEach {
            Assert.assertFalse(teamA.contains(it))
            Assert.assertFalse(teamB.contains(it))
        }
    }

}

private fun generatePlayers(amount: Int): Set<Player> =
    List(amount) {
        Player("player $it")
    }.toSet()
