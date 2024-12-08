package github.mahdiasd.filepicker.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import github.mahdiasd.filepicker.utils.FileProvider
import github.mahdiasd.filepicker.utils.PickerConfig
import github.mahdiasd.filepicker.utils.PickerFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AudioListContent(
    pickerConfig: PickerConfig,
    selected: (ImmutableList<PickerFile>) -> Unit,
) {
    val context = LocalContext.current
    var audios by remember { mutableStateOf<ImmutableList<PickerFile>>(persistentListOf()) }
    val selectedFiles: SnapshotStateList<PickerFile> = remember { mutableStateListOf() }
    var searchText by remember { mutableStateOf("") }

    val searchedAudios by remember(selectedFiles) {
        derivedStateOf { if (searchText.isEmpty()) audios else audios.filter { it.file.name.contains(searchText) }.toImmutableList() }
    }

    LaunchedEffect(Unit) { audios = FileProvider.getAudio(context) }

    Box(modifier = Modifier.fillMaxWidth()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = searchText,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = pickerConfig.dialogContainerColor,
                        focusedContainerColor = pickerConfig.dialogContainerColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { searchText = it },
                    maxLines = 1,
                    textStyle = pickerConfig.audioListScreenConfig.searchBoxTextStyle,
                    minLines = 1,
                    placeholder = {
                        Text("Search audio", style = pickerConfig.audioListScreenConfig.searchBoxTextStyle)
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(pickerConfig.audioListScreenConfig.searchBoxIcon),
                            contentDescription = "search on musics",
                            tint = pickerConfig.audioListScreenConfig.searchBoxIconColor
                        )
                    }
                )
            }

            items(items = searchedAudios) { pickerFile: PickerFile ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)
                ) {
                    CircleCheckbox(
                        modifier = Modifier,
                        selected = selectedFiles.find { it.path == pickerFile.path } != null,
                        selectedColor = pickerConfig.multiMediaScreenConfig.checkBoxSelectedColor,
                        unSelectedColor = pickerConfig.multiMediaScreenConfig.checkBoxUnSelectedColor,
                        iconSize = 32.dp,
                        onChecked = {
                            if (selectedFiles.find { it.path == pickerFile.path } == null) selectedFiles.add(pickerFile)
                            else selectedFiles.remove(pickerFile)
                        }
                    )


                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    if (selectedFiles.find { it.path == pickerFile.path } == null) {
                                        selectedFiles.add(pickerFile)
                                    } else {
                                        selectedFiles.remove(pickerFile)
                                    }
                                }
                            ),
                        text = pickerFile.file.name,
                        style = pickerConfig.audioListScreenConfig.audioItemTextStyle,
                        color = if (selectedFiles.find { it.path == pickerFile.path } == null) {
                            pickerConfig.audioListScreenConfig.audioUnselectedItemColor
                        }else
                            pickerConfig.audioListScreenConfig.audioSelectedItemColor
                    )
                }
            }

            item {
                Spacer(Modifier.height(50.dp))
            }
        }

        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            shape = pickerConfig.audioListScreenConfig.confirmButtonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = pickerConfig.audioListScreenConfig.confirmButtonContainerColor,
            ),
            onClick = { selected(selectedFiles.toImmutableList()) }
        ) {
            Text(
                text = pickerConfig.audioListScreenConfig.confirmButtonText,
                style = pickerConfig.audioListScreenConfig.confirmButtonTextStyle,
                color = pickerConfig.audioListScreenConfig.confirmButtonTextColor
            )
        }

    }


}


@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return density * value
}
