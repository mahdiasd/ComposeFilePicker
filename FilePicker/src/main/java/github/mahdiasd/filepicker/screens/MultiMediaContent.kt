package github.mahdiasd.filepicker.screens

import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import github.mahdiasd.filepicker.utils.PickerFile
import github.mahdiasd.filepicker.utils.PickerType
import github.mahdiasd.filepicker.utils.FileProvider
import github.mahdiasd.filepicker.utils.PickerConfig

@Composable
internal fun MultiMediaContent(
    pickerConfig: PickerConfig,
    type: PickerType,
    selected: (ImmutableList<PickerFile>) -> Unit,
) {
    val context = LocalContext.current
    var mediaList by remember { mutableStateOf<ImmutableList<PickerFile>>(persistentListOf()) }
    val selectedFiles: SnapshotStateList<PickerFile> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        mediaList = when (type) {
            PickerType.ImageAndVideo -> (FileProvider.getImage(context, false) + FileProvider.getVideo(context))
            PickerType.ImageOnly -> FileProvider.getImage(context, false)
            PickerType.VideoOnly -> FileProvider.getVideo(context)
            else -> {
                listOf()
            }
        }.toImmutableList()
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clipScrollableContainer(Orientation.Vertical),
            contentPadding = PaddingValues(
                top = 16.dp,
                end = 16.dp,
                start = 16.dp,
                bottom = 150.dp
            )
        ) {
            items(items = mediaList, key = { it.path }) { pickerFile ->
                MediaItem(
                    pickerFile = pickerFile,
                    config = pickerConfig,
                    isSelected = selectedFiles.find { it.path == pickerFile.path } != null,
                    onChecked = {
                        if (selectedFiles.find { it.path == pickerFile.path } == null) {
                            selectedFiles.add(pickerFile)
                        } else {
                            selectedFiles.remove(pickerFile)
                        }
                    }
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            shape = pickerConfig.multiMediaScreenConfig.confirmButtonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = pickerConfig.multiMediaScreenConfig.confirmButtonContainerColor,
            ),
            onClick = { selected(selectedFiles.toImmutableList()) }
        ) {
            Text(
                text = pickerConfig.multiMediaScreenConfig.confirmButtonText,
                style = pickerConfig.multiMediaScreenConfig.confirmButtonTextStyle,
                color = pickerConfig.multiMediaScreenConfig.confirmButtonTextColor
            )
        }
    }


}