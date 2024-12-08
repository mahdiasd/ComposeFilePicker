package github.mahdiasd.composefilepicker.utils

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
sealed class PickerType {
    data object ImageAndVideo : PickerType()

    data object ImageOnly : PickerType()

    data object VideoOnly : PickerType()

    data object Audio : PickerType()

    data object Storage : PickerType()
}

fun PickerType.getPermissions(): ImmutableList<String> {
    val list: MutableList<String> = mutableListOf()
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    } else {
        when (this) {
            PickerType.Audio -> list.add(Manifest.permission.READ_MEDIA_AUDIO)
            PickerType.Storage -> list.addAll(listOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO))
            PickerType.ImageAndVideo -> list.addAll(listOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO))
            PickerType.ImageOnly -> list.add(Manifest.permission.READ_MEDIA_IMAGES)
            PickerType.VideoOnly -> list.add(Manifest.permission.READ_MEDIA_VIDEO)
        }
    }
    return list.toImmutableList()
}
