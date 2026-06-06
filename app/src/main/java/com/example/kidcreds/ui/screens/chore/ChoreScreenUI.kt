package com.example.kidcreds.ui.screens.chore

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kidcreds.data.local.table.ChoreEntity
import com.example.kidcreds.data.local.table.ChoreFrequency
import com.example.kidcreds.data.local.table.ChorePriority
import com.example.kidcreds.ui.theme.*

@Composable
fun ChoreScreenUI(choreViewModel: ChoreViewModel) {
    val chores by choreViewModel.uiState.collectAsStateWithLifecycle()
    ChoreScreenContent(chores = chores)
}

@Composable
fun ChoreScreenContent(chores: List<ChoreEntity>) {
    val colorScheme = MaterialTheme.colorScheme
    
    val totalChores = chores.size
    val completedChores = chores.count { it.isCompleted }
    val totalPoints = chores.filter { it.isCompleted }.sumOf { it.points }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorScheme.secondary,
                                colorScheme.secondaryContainer
                            )
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Debug Log
                Log.d("CHORE", "UI Redraw: Received ${chores.size} items from database")
                
                // 1. Header Section
        item {
            HeaderSection(totalChores, completedChores, totalPoints)
        }
        
        // 2. Navigation Row
        item {
            Spacer(modifier = Modifier.height(16.dp))
            NavigationRow()
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // 3. Title and Add Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Chores",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DB6AC)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text(text = "Add Chore", fontSize = 14.sp)
                }
            }
        }
        
        // 4. Chore Sections
        val requiredChores = chores.filter { it.priority.name == "MUST" }
        val optionalChores = chores.filter { it.priority.name == "OPTIONAL" }
        
        if (requiredChores.isNotEmpty()) {
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader("REQUIRED")
                }
            }
            items(requiredChores) { chore ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ChoreCard(chore)
                }
            }
        }
        
        if (optionalChores.isNotEmpty()) {
            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader("OPTIONAL")
                }
            }
            items(optionalChores) { chore ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ChoreCard(chore)
                }
            }
        }
        
        // Bottom Spacer for better scroll reach
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HeaderSection(total: Int, completed: Int, points: Int) {
    val colorScheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorScheme.primary,
                        colorScheme.primaryContainer
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "KidChores",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Track chores, earn rewards! 👑",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(value = total.toString(), label = "Total Chores", modifier = Modifier.weight(1f))
                SummaryCard(value = completed.toString(), label = "Completed", modifier = Modifier.weight(1f))
                SummaryCard(value = points.toString(), label = "Points", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SummaryCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun NavigationRow() {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NavButton(
            icon = Icons.AutoMirrored.Filled.List,
            label = "Chores",
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
            modifier = Modifier.weight(1f)
        )
        NavButton(
            icon = Icons.Default.Face,
            label = "Kids",
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        NavButton(
            icon = Icons.Default.Add,
            label = "Add Kid",
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NavButton(
    icon: ImageVector,
    label: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /* TODO */ },
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SectionHeader(label: String) {
    val colorScheme = MaterialTheme.colorScheme
    val badgeColor = if (label == "REQUIRED") colorScheme.tertiaryContainer else colorScheme.surfaceVariant
    val textColor = if (label == "REQUIRED") colorScheme.onTertiaryContainer else colorScheme.onSurfaceVariant

    Surface(
        color = badgeColor,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun ChoreCard(chore: ChoreEntity) {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle Checkbox placeholder
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            )
            // Icon
            val icon = when (chore.title) {
                "Brush Teeth" -> Icons.Default.CleaningServices
                "Make Your Bed" -> Icons.Default.Bed
                "Empty Dishwasher" -> Icons.Default.Countertops
                else -> Icons.AutoMirrored.Filled.List
            }
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF42A5F5)
            )
            
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chore.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Badge(
                        text = chore.priority.name,
                        containerColor = if (chore.priority.name == "MUST") colorScheme.tertiaryContainer else colorScheme.surfaceVariant,
                        contentColor = if (chore.priority.name == "MUST") colorScheme.onTertiaryContainer else colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = chore.frequency.name,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Points Icon placeholder
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(colorScheme.tertiary)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = chore.points.toString(),
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.tertiary,
                        fontSize = 16.sp
                    )
                }
                // Day indicators placeholder
                Text(text = "E S S", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun Badge(text: String, containerColor: Color, contentColor: Color) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChoreScreenLightPreview() {
    val mockChores = listOf(
        ChoreEntity(
            title = "Brush Teeth",
            description = "Morning and Night",
            points = 1,
            priority = ChorePriority.MUST,
            frequency = ChoreFrequency.DAILY,
            assignedTo = "Rahul"
        ),
        ChoreEntity(
            title = "Make Your Bed",
            description = "Tidy up",
            points = 1,
            priority = ChorePriority.MUST,
            frequency = ChoreFrequency.DAILY,
            assignedTo = "Rahul"
        ),
        ChoreEntity(
            title = "Empty Dishwasher",
            description = "Help out",
            points = 5,
            priority = ChorePriority.OPTIONAL,
            frequency = ChoreFrequency.AS_NEEDED,
            assignedTo = "Rahul"
        )
    )
    KidCredsTheme(darkTheme = false) {
        ChoreScreenContent(chores = mockChores)
    }
}

@Preview(showBackground = true)
@Composable
fun ChoreScreenDarkPreview() {
    val mockChores = listOf(
        ChoreEntity(
            title = "Brush Teeth",
            description = "Morning and Night",
            points = 1,
            priority = ChorePriority.MUST,
            frequency = ChoreFrequency.DAILY,
            assignedTo = "Rahul"
        )
    )
    KidCredsTheme(darkTheme = true) {
        ChoreScreenContent(chores = mockChores)
    }
}
