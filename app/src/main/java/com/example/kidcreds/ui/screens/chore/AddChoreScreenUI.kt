package com.example.kidcreds.ui.screens.chore

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kidcreds.data.local.table.ChoreEntity
import com.example.kidcreds.ui.components.AppTextField
import com.example.kidcreds.ui.theme.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.kidcreds.data.local.table.ChoreFrequency
import com.example.kidcreds.data.local.table.ChorePriority

@Composable
fun AddChoreScreenUI(onBackClick: () -> Unit = {},vm: ChoreViewModel) {
    var choreName by remember { mutableStateOf("Homework") }
    var description by remember { mutableStateOf("Complete all school tasks") }
    var points by remember { mutableStateOf("5") }
    var assignedTo by remember { mutableStateOf("") }
    var lastCompletedDate by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(ChorePriority.MUST) }
    var selectedIcon by remember { mutableStateOf("📚") }
    val context = LocalContext.current
    val pOptions = listOf<ChorePriority>(ChorePriority.MUST, ChorePriority.OPTIONAL)
    val fOptions = listOf("DAILY", "AS_NEEDED")

    val icons = listOf("📋", "🛌", "🗒️", "🍽️", "🗑️", "📚", "🐕", "🌱")
    
    // Dynamic theme colors from MaterialTheme
    val colorScheme = MaterialTheme.colorScheme

    val darkBg = colorScheme.background
    val cardBg = colorScheme.surfaceContainer
    val itemBg = colorScheme.surfaceContainerHigh
    val glowColor = colorScheme.secondary // Mapped to choreBackgroundGradientStart in Theme.kt
    
    // Header colors can also be dynamic
    val headerStart = if (MaterialTheme.colorScheme.primary == choreHeaderGradientStartDark) choreHeaderGradientStartDark else choreHeaderGradientStart
    val headerEnd = if (MaterialTheme.colorScheme.primaryContainer == choreHeaderGradientEndDark) choreHeaderGradientEndDark else choreHeaderGradientEnd

    LaunchedEffect(true) {
        vm.event.collect {
            when(it){
                is ChoreUiState.OnChoreInserted -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ChoreUiState.ShowError -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. Gradient Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(headerStart, headerEnd)
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Create Chore ",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Add a new task for your kids",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // 2. Icon Selection Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Preview Circle with Glow
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, glowColor, CircleShape)
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        ambientColor = glowColor,
                        spotColor = glowColor
                    )
                    .background(itemBg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = selectedIcon, fontSize = 48.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Icon Grid
            FlowRow(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                maxItemsInEachRow = 4
            ) {
                icons.forEach { icon ->
                    val isSelected = selectedIcon == icon
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(itemBg)
                            .then(
                                if (isSelected) {
                                    Modifier.border(2.dp, glowColor, CircleShape)
                                } else Modifier
                            )
                            .clickable { selectedIcon = icon },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = icon, fontSize = 32.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Chore Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "CHORE DETAILS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(20.dp))

                DetailLabel("Chore Name", colorScheme.onSurface)
                AppTextField(
                    value = choreName,
                    onValueChange = { choreName = it },
                    placeholder = {},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = itemBg,
                        unfocusedContainerColor = itemBg,
                        focusedBorderColor = colorScheme.onSurface.copy(alpha = 0.2f),
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface
                    ),
                    maxLines = 3
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                DetailLabel("Description", colorScheme.onSurface)
                AppTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Describe the task",color = colorScheme.onSurface.copy(alpha = 0.5f)) },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = itemBg,
                        unfocusedContainerColor = itemBg,
                        focusedBorderColor = colorScheme.onSurface.copy(alpha = 0.2f),
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface
                    ),
                )
                DetailLabel("Points", colorScheme.onSurface)
                AppTextField(
                    value = points,
                    onValueChange = { points = it },
                    placeholder = { Text("Enter Points",color = colorScheme.onSurface.copy(alpha = 0.5f)) },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = itemBg,
                        unfocusedContainerColor = itemBg,
                        focusedBorderColor = colorScheme.onSurface.copy(alpha = 0.2f),
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface
                    ),
                )
                DetailLabel("Assigned To", colorScheme.onSurface)
                AppTextField(
                    value = assignedTo,
                    onValueChange = { assignedTo = it },
                    placeholder = { Text("Enter Name",color = colorScheme.onSurface.copy(alpha = 0.5f)) },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = itemBg,
                        unfocusedContainerColor = itemBg,
                        focusedBorderColor = colorScheme.onSurface.copy(alpha = 0.2f),
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface
                    ),
                )
                DetailLabel("Last Completed Date", colorScheme.onSurface)
                AppTextField(
                    value = lastCompletedDate,
                    onValueChange = { lastCompletedDate = it },
                    placeholder = { Text("Enter Date",color = colorScheme.onSurface.copy(alpha = 0.5f)) },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = itemBg,
                        unfocusedContainerColor = itemBg,
                        focusedBorderColor = colorScheme.onSurface.copy(alpha = 0.2f),
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.1f),
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface
                    ),
                )
                DetailLabel("Priority", colorScheme.onSurface)
                OptionsDropdownMenu(
                    selected = selected,
                    onSelected = { selected = it },
                    options = pOptions
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val chore = ChoreEntity(
                        title = choreName,
                        description = description,
                        points = points.toInt(),
                        assignedTo = assignedTo,
                        priority = selected
                    )
                    vm.addChore(chore)
                }) {
                    Text("Done")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DetailLabel(text: String, color: Color) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun AddChoreScreenDarkPreview() {
    KidCredsTheme(darkTheme = true) {
//        AddChoreScreenUI()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun AddChoreScreenLightPreview() {
    KidCredsTheme(darkTheme = false) {
//        AddChoreScreenUI()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsDropdownMenu(
    selected: ChorePriority,
    onSelected: (ChorePriority) -> Unit,
    options: List<ChorePriority>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selected.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                focusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.name) },
                    onClick = {
                        onSelected(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}