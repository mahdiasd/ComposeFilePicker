@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package github.mahdiasd.composefilepicker.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.Companion.isPhotoPickerAvailable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import github.mahdiasd.composefilepicker.utils.PickerResult
import github.mahdiasd.composefilepicker.utils.OnResume
import github.mahdiasd.composefilepicker.utils.PickerConfig
import github.mahdiasd.composefilepicker.utils.PickerFile
import github.mahdiasd.composefilepicker.utils.PickerType
import github.mahdiasd.composefilepicker.utils.getPermissions
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PickerDialog(
    modifier: Modifier = Modifier.fillMaxWidth(),
    types: ImmutableList<PickerType>,
    pickerConfig: PickerConfig,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceAround,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    selected: (ImmutableList<PickerResult>) -> Unit
) {
    val context = LocalContext.current

    var dismissWhenOnResume by remember { mutableStateOf(false) }
    var isPermissionGrant by remember { mutableStateOf(false) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var dialogAlpha by remember { mutableFloatStateOf(0f) }

    val isPhotoPickerAvailable by remember { mutableStateOf(isPhotoPickerAvailable(context)) }

    val pickMultipleMedia = if (pickerConfig.maxSelection > 1) {
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(pickerConfig.maxSelection)) { uris ->
            if (uris.isNotEmpty()) {
                selected(uris.toPickerResults(context))
            }
        }
    } else {
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selected(listOf(uri).toPickerResults(context))
            }
        }
    }

    val pickFile = if (pickerConfig.maxSelection > 1) {
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            if (uris.isNotEmpty()) {
                selected(uris.toPickerResults(context))
            }
        }
    } else {
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                selected(listOf(uri).toPickerResults(context))
            }
        }
    }

    val showPickerDialog by remember(types) { mutableStateOf(types.size > 1) }

    var selectedType: PickerType? by remember(showPickerDialog, types) { mutableStateOf(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { grantResults ->
            if (grantResults.all { it.value }) {
                isPermissionGrant = true
                Log.d("permissionLauncher", "PickerDialog: $isPermissionGrant")
            }
        }
    )

    OnResume {
        if (dismissWhenOnResume) onDismiss()
    }


    LaunchedEffect(types, showPickerDialog) {
        if (selectedType == null && !showPickerDialog) {
            selectedType = types.firstOrNull()
        }
    }

    LaunchedEffect(selectedType) {
        dialogAlpha = when (selectedType) {
            PickerType.Audio -> 1f
            PickerType.ImageAndVideo -> if (isPhotoPickerAvailable) 0f else 1f
            PickerType.ImageOnly -> if (isPhotoPickerAvailable) 0f else 1f
            PickerType.Storage -> 0f
            PickerType.VideoOnly -> if (isPhotoPickerAvailable) 0f else 1f
            null -> if (showPickerDialog && selectedType == null) 1f else 0f
        }
    }


    ModalBottomSheet(
        modifier = Modifier.alpha(dialogAlpha),
        onDismissRequest = { onDismiss() },
        containerColor = pickerConfig.dialogContainerColor,
        sheetState = sheetState
    ) {
        if (showPickerDialog && selectedType == null) {
            PickerItemContent(
                modifier = modifier,
                types = types,
                pickerConfig = pickerConfig,
                verticalAlignment = verticalAlignment,
                horizontalArrangement = horizontalArrangement,
                onItemSelect = {
                    selectedType = it
                },
            )
        } else {
            when (selectedType) {
                PickerType.Audio -> {
                    if (isPermissionGrant)
                        AudioListContent(
                            pickerConfig = pickerConfig,
                            selected = {
                                selected(it.toPickerResults())
                            }
                        )
                    else {
                        permissionLauncher.launch(selectedType!!.getPermissions().toTypedArray())
                    }
                }

                PickerType.ImageAndVideo -> {
                    if (isPhotoPickerAvailable) {
                        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            Log.d("TAG", "PickerDialog: ImageAndVideo")
                            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                            dismissWhenOnResume = true
                        }
                    } else if (isPermissionGrant) {
                        MultiMediaContent(
                            pickerConfig = pickerConfig,
                            type = selectedType!!,
                            selected = { selected(it.toPickerResults()) }
                        )
                    } else {
                        permissionLauncher.launch(selectedType!!.getPermissions().toTypedArray())
                    }
                }

                PickerType.ImageOnly -> {
                    if (isPhotoPickerAvailable) {
                        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            dismissWhenOnResume = true
                        }
                    } else if (isPermissionGrant) {
                        MultiMediaContent(
                            pickerConfig = pickerConfig,
                            type = selectedType!!,
                            selected = { selected(it.toPickerResults()) }
                        )
                    } else {
                        permissionLauncher.launch(selectedType!!.getPermissions().toTypedArray())
                    }
                }

                PickerType.Storage -> {
                    if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        pickFile.launch(arrayOf("*/*"))
                        dismissWhenOnResume = true
                    }
                }

                PickerType.VideoOnly -> {
                    if (isPhotoPickerAvailable) {
                        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                            dismissWhenOnResume = true
                        }
                    } else if (isPermissionGrant) {
                        MultiMediaContent(
                            pickerConfig = pickerConfig,
                            type = selectedType!!,
                            selected = { selected(it.toPickerResults()) }
                        )
                    } else {
                        permissionLauncher.launch(selectedType!!.getPermissions().toTypedArray())
                    }
                }

                else -> {}
            }
        }
    }
}

internal fun ImmutableList<PickerFile>.toPickerResults(): ImmutableList<PickerResult> {
    return this.map {
        val bitmap = try {
            BitmapFactory.decodeFile(it.path).asImageBitmap()
        } catch (e: Exception) {
            null
        }
        PickerResult(Uri.fromFile(it.file), bitmap)
    }.toImmutableList()
}

internal fun List<Uri>.toPickerResults(context: Context): ImmutableList<PickerResult> {
    return this.map { uri ->
        val imageBitmap = try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap?.asImageBitmap()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null if decoding fails or an exception occurs
        }
        // Create the PickerResult with the imageBitmap (may be null)
        PickerResult(uri = uri, imageBitmap = imageBitmap)
    }.toImmutableList()
}