package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.features.randomteams.generateRandomColor
import br.com.alexf.boraprofut.features.randomteams.randomTeams
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class TeamDrawerUseCaseTest {

    private val teamDrawer = TeamDrawerUseCase()

    @Test
    fun shouldDrawTeamPlayersRandomlyGivenAmount() {
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

    @Test
    fun shouldDrawTeamsPlayerBalancedGivenAmount() {
        val players = List(10){
            Player(name = "jogador ${it + 1}", 12)
        }.toSet()
        val drawnTeams = teamDrawer
            .drawBalancedTeams(players, 4)
        drawnTeams.map { teams ->
            teams.sumOf { it.level }
        }.let { weights ->
            println(weights)
        }
    }

}

private fun generatePlayers(amount: Int): Set<Player> =
    List(amount) {
        Player(name = "player $it", level = Random.nextInt(1, 10))
    }.toSet()
