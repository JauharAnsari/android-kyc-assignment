package com.example.kycflow.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kycflow.R

@Composable
fun StatusBadge(isVerified: Boolean, modifier: Modifier = Modifier) {
    val backgroundColor = if (isVerified) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
    val textColor = if (isVerified) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
    val text = if (isVerified) stringResource(R.string.status_verified) else stringResource(R.string.status_pending)

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
