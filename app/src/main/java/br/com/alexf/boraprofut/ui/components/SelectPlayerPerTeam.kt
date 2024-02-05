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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF673AB7),
                        Color(0xFFF44336)
                    )
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.players_amount_per_team),
            style = MaterialTheme.typography.titleLarge
                .copy(color = Color.White)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            Modifier.height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconModifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.decreases_players_amount_per_team),
                iconModifier
                    .clickable { onDecreasePlayers() },
                tint = Color.White
            )
            Box(Modifier.fillMaxHeight()) {
                Text(
                    text = "$players",
                    Modifier
                        .align(Alignment.Center),
                    style = LocalTextStyle.current.copy(
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.increases_players_amount_per_team),
                iconModifier
                    .clickable { onIncreasePlayers() },
                tint = Color.White
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