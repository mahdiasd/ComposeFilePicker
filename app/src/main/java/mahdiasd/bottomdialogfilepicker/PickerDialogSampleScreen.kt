package mahdiasd.bottomdialogfilepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import github.mahdiasd.filepicker.screens.PickerDialog
import github.mahdiasd.filepicker.utils.PickerConfig
import github.mahdiasd.filepicker.utils.PickerFile
import github.mahdiasd.filepicker.utils.PickerType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerDialogSampleScreen() {
    // State to manage the visibility of the PickerDialog
    var showPicker by remember { mutableStateOf(false) }

    // State to store the selected files
    var selectedFiles by remember { mutableStateOf(persistentListOf<PickerFile>()) }
    var selectedTypes by remember { mutableStateOf(persistentListOf<PickerType>()) }

    // Define your PickerConfig as needed
    val pickerConfig = PickerConfig(
        maxSelection = 5,
        // Add other configurations as needed
    )

    // Define all PickerTypes you want to showcase
    val pickerTypes = persistentListOf(
        PickerType.Audio,
        PickerType.ImageAndVideo,
        PickerType.Storage,
        PickerType.ImageOnly,
        PickerType.VideoOnly
    )

    // Scaffold to structure the UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PickerDialog Sample") }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        selectedTypes = pickerTypes.filterNot { it == PickerType.ImageOnly || it == PickerType.VideoOnly }.toPersistentList()
                        showPicker = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Open Item Picker")
                }
                // Buttons for each PickerType
                pickerTypes.forEach { type ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            selectedTypes = persistentListOf(type)
                            showPicker = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Open $type Picker")
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                // Display selected files
                if (selectedFiles.isNotEmpty()) {
                    Text(
                        text = "Selected Files:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(selectedFiles.size) { index ->
                            Text(text = "${index + 1} → " + selectedFiles[index].path)
                        }
                    }
                }
            }

            // Show the PickerDialog when showPicker is true
            if (showPicker && selectedTypes.isNotEmpty()) {
                PickerDialog(
                    types = selectedTypes,
                    pickerConfig = pickerConfig,
                    onDismiss = { showPicker = false },
                    selected = { files ->
                        selectedFiles = files.toPersistentList()
                        showPicker = false
                    }
                )
            }
        }
    )
}