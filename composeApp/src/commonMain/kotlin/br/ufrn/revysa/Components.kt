package br.ufrn.revysa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun Screen(
    title: String,
    content: LazyListScope.() -> Unit,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier.widthIn(max = 760.dp).fillMaxSize().testTag("screen"),
            state = rememberLazyListState(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text(title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.semantics { heading() })
            }
            content()
        }
    }
}

@Composable
internal fun Action(
    label: String,
    tag: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag(tag)) {
        Text(label)
    }
}

@Composable
internal fun MaterialCard(
    material: StudyMaterial,
    progress: Float,
    onClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("material-${material.id}"),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(material.subject, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(material.title, style = MaterialTheme.typography.titleLarge)
            Text(material.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            ReadingProgress(progress)
        }
    }
}

@Composable
internal fun ReadingProgress(progress: Float) {
    LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
    Text("${(progress * 100).toInt()}% lido", style = MaterialTheme.typography.labelLarge)
}

@Composable
internal fun EmptyMessage(message: String) {
    Text(message, modifier = Modifier.testTag("empty"), color = MaterialTheme.colorScheme.onSurfaceVariant)
}
