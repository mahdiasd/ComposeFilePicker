package github.mahdiasd.composefilepicker.utils

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap

@Stable
data class PickerResult(
    val uri: Uri,

    /**
     * imageBitmap is not null only when it is an image.
     * */
    val imageBitmap: ImageBitmap? = null
)
