package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.models.Player
import org.amshove.kluent.shouldNotBeGreaterThan
import org.amshove.kluent.shouldNotContainAny
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
        teams.forEach { team ->
            teams.filter {
                it != team
            } shouldNotContainAny team
        }
    }

    @Test
    fun shouldDrawTeamsPlayerBalancedGivenAmount() {
        val players = generatePlayers(16)
        val drawnTeams = teamDrawer
            .drawBalancedTeams(players, 4)
        val teamAverages = drawnTeams.map { teams ->
            teams.sumOf { it.level } / teams.size
        }
        teamAverages.forEach { average ->
            teamAverages.forEach {
                (it - average).shouldNotBeGreaterThan(1)
            }
        }
    }

}

private fun generatePlayers(amount: Int): Set<Player> =
    List(amount) {
        Player(name = "jogador ${it + 1}", level = Random.nextInt(1, 10))
    }.toSet()
