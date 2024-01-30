package br.com.alexf.boraprofut.features.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Save
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.ui.components.BoraProFutButton

@Composable
fun ScaffoldTopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(80.dp).padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtomFromTopBar(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.save).toUpperCase(Locale.current),
            contentDescription = stringResource(R.string.draw_teams_again_icon),
            icons =   Icons.Outlined.Save,
            onClick = {}
        )
        ButtomFromTopBar(
            modifier = Modifier.weight(1f),
            label = stringResource(R.string.clear).toUpperCase(Locale.current),
            contentDescription = stringResource(R.string.draw_teams_again_icon),
            icons = Icons.Outlined.Clear,
            onClick = {}
        )
    }
}

@Composable
private fun ButtomFromTopBar(
    modifier: Modifier = Modifier,
    label: String,
    contentDescription: String,
    icons: ImageVector,
    onClick: () -> Unit,
){
    BoraProFutButton(
        modifier = modifier,
        onClick = {onClick()},
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    Modifier.weight(1f),
                    fontSize = 14.sp,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    icons,
                    contentDescription = contentDescription,
                    Modifier.clip(CircleShape)
                )
            }
        }
    }
}