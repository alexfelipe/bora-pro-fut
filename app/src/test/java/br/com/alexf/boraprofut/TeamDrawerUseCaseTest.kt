package br.com.alexf.boraprofut

import br.com.alexf.boraprofut.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.models.Player
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainAny
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeGreaterThan
import org.amshove.kluent.shouldNotContainAny
import org.junit.Test
import kotlin.random.Random

private const val GOAL_KEEPER = "Goalkeeper"

class TeamDrawerUseCaseTest {

    private val teamDrawer = TeamDrawerUseCase()

    @Test
    fun shouldDrawTeamPlayersRandomlyGivenAmount() {
        val players = generatePlayers(12)
        val teams = teamDrawer.drawRandomTeams(players, 4)
        teams.size shouldBeEqualTo 3
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
                (it - average) shouldNotBeGreaterThan 2
            }
        }
    }

    @Test
    fun shouldContainGoalKeeperInAllTeams() {
        val players = generatePlayers(8).toMutableSet()
        val playersPerTeam = 5
        players.addAll(
            setOf(
                Player("$GOAL_KEEPER 1", isGoalKeeper = true),
                Player("$GOAL_KEEPER 2", isGoalKeeper = true),
            )
        )
        val drawnTeams = teamDrawer.drawRandomTeams(players = players, playersPerTeam = playersPerTeam)
        drawnTeams.size shouldBeEqualTo 2
        drawnTeams.forEach { team ->
            team shouldContainAny {
                it.isGoalKeeper
            }
            team.size shouldBeEqualTo playersPerTeam
        }
    }

    @Test
    fun shouldNotContainGoalKeeperInAllTeams() {
        val players = generatePlayers(10)
        val playersPerTeam = 5
        val drawnTeams = teamDrawer.drawRandomTeams(players = players, playersPerTeam = playersPerTeam)
        drawnTeams.size shouldBeEqualTo 2
        drawnTeams.forEach { team ->
            team shouldNotContainAny  {
                it.isGoalKeeper
            }
            team.size shouldBeEqualTo playersPerTeam
        }
    }

    @Test
    fun shouldContainGoalKeeperJustInTheFirstTeam() {
        val players = generatePlayers(9).toMutableSet()
        val playersPerTeam = 5
        val goalKeeper = Player(GOAL_KEEPER, isGoalKeeper = true)
        players.add(goalKeeper)
        val drawnTeams = teamDrawer.drawRandomTeams(players = players, playersPerTeam = playersPerTeam)
        drawnTeams.size shouldBeEqualTo 2
        drawnTeams[0] shouldContain goalKeeper
        drawnTeams[1] shouldNotContainAny {
            it.isGoalKeeper
        }
        drawnTeams.forEach {team ->
            team.size shouldBeEqualTo playersPerTeam
        }
    }

    @Test
    fun shouldContainATeamWithoutASufficientNumberOfPlayers() {
        var players = generatePlayers(9)
        val playersPerTeam = 4
        var drawnTeams =
            teamDrawer.drawRandomTeams(players = players, playersPerTeam = playersPerTeam)
        drawnTeams.last().size shouldNotBeEqualTo playersPerTeam
        players = generatePlayers(11)
        drawnTeams =
            teamDrawer.drawBalancedTeams(players = players, playersPerTeam = playersPerTeam)
        drawnTeams.last().size shouldNotBeEqualTo playersPerTeam
    }

}

private fun generatePlayers(amount: Int): Set<Player> =
    List(amount) {
        Player(name = "player ${it + 1}", level = Random.nextInt(1, 10))
    }.toSet()
