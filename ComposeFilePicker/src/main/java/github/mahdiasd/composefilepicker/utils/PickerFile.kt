package github.mahdiasd.composefilepicker.utils

import androidx.compose.runtime.Stable
import java.io.File

@Stable
internal data class PickerFile(
    val path: String,
    val file: File = File(path),
)
