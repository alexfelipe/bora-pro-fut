package br.com.alexf.futzinmaroto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import br.com.alexf.futzinmaroto.ui.theme.FutzinMarotoTheme

@Composable
fun FutzinMarotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(25))
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        content()
    }
}

@Preview
@Composable
fun FutzinMarotoButtonPreview() {
    FutzinMarotoTheme {
        FutzinMarotoButton(onClick = { /*TODO*/ }) {
            Text(text = LoremIpsum(2).values.first())
        }
    }
}