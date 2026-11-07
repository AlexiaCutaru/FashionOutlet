package com.example.outletfashion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.outletfashion.data.local.OutfitDatabase
import com.example.outletfashion.data.local.OutfitEntity
import kotlinx.coroutines.launch

@Composable
fun MyOutfitsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val database = remember(context) {
        OutfitDatabase.getDatabase(context)
    }
    val dao = remember(database) {
        database.outfitDao()
    }

    val outfits by dao.getAllOutfits()
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    var editingOutfit by remember { mutableStateOf<OutfitEntity?>(null) }

    var name by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    fun openCreateDialog() {
        editingOutfit = null
        name = ""
        style = ""
        budget = ""
        showDialog = true
    }

    fun openEditDialog(outfit: OutfitEntity) {
        editingOutfit = outfit
        name = outfit.name
        style = outfit.style
        budget = outfit.budget.toString()
        showDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← BACK")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "MY OUTFITS",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Create and manage your looks",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { openCreateDialog() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("+ CREATE NEW OUTFIT")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (outfits.isEmpty()) {
            Text("No outfits saved yet.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = outfits,
                    key = { it.id }
                ) { outfit ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = outfit.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Style: ${outfit.style}")
                            Text("Budget: ${outfit.budget} LEI")

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { openEditDialog(outfit) }
                                ) {
                                    Text("EDIT")
                                }

                                OutlinedButton(
                                    onClick = {
                                        scope.launch {
                                            dao.deleteOutfit(outfit)
                                        }
                                    }
                                ) {
                                    Text("DELETE")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    if (editingOutfit == null)
                        "Create Outfit"
                    else
                        "Edit Outfit"
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Outfit name") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = style,
                        onValueChange = { style = it },
                        label = { Text("Style") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = budget,
                        onValueChange = { budget = it },
                        label = { Text("Budget (LEI)") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = name.isNotBlank() &&
                            style.isNotBlank() &&
                            (budget.toDoubleOrNull()?.let { it >= 0.0 } == true),
                    onClick = {
                        val budgetValue = budget.toDoubleOrNull()
                            ?: return@TextButton

                        val outfit = OutfitEntity(
                            id = editingOutfit?.id ?: 0,
                            name = name.trim(),
                            style = style.trim(),
                            budget = budgetValue
                        )

                        scope.launch {
                            if (editingOutfit == null) {
                                dao.insertOutfit(outfit)
                            } else {
                                dao.updateOutfit(outfit)
                            }
                        }

                        showDialog = false
                    }
                ) {
                    Text("SAVE")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("CANCEL")
                }
            }
        )
    }
}