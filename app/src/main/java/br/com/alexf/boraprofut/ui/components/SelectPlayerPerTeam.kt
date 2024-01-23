package br.com.alexf.boraprofut.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun SelectPlayerPerTeam(
    players: Int,
    onDecreasePlayers: () -> Unit,
    onIncreasePlayers: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .clip(RoundedCornerShape(15))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.players_amount_per_team),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            Modifier.height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconModifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(R.string.decreases_players_amount_per_team),
                iconModifier
                    .clickable { onDecreasePlayers() }
            )
            Box(Modifier.fillMaxHeight()) {
                Text(
                    text = "$players",
                    Modifier
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = stringResource(R.string.increases_players_amount_per_team),
                iconModifier
                    .clickable { onIncreasePlayers() }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPlayerPerTeamPreview() {
    BoraProFutTheme {
        SelectPlayerPerTeam(
            1,
            onIncreasePlayers = {},
            onDecreasePlayers = {}
        )
    }
}