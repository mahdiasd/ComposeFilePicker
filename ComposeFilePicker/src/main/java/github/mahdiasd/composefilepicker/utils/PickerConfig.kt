package github.mahdiasd.composefilepicker.utils

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import github.mahdiasd.composefilepicker.theme.DialogContainerColor
import github.mahdiasd.composefilepicker.theme.IconColor
import github.mahdiasd.composefilepicker.theme.IconContainerColor
import github.mahdiasd.composefilepicker.theme.TextColor
import github.mahdiasd.composefilepicker.R

@Stable
data class PickerConfig(
    val maxSelection: Int,

    val imageTitle: String = "Image",
    val videoTitle: String = "Video",
    val audioTitle: String = "Audio",
    val storageTitle: String = "Storage",
    val imageAndVideoTitle: String = "Image & Video",

    val imageIcon: Int = R.drawable.mahdiasd_ic_image,
    val videoIcon: Int = R.drawable.mahdiasd_ic_video,
    val audioIcon: Int = R.drawable.mahdiasd_ic_audio,
    val storageIcon: Int = R.drawable.mahdiasd_ic_storage,
    val imageVideoIcon: Int = R.drawable.mahdiasd_ic_image,

    val iconColor: Color = IconColor,
    val iconShape: Shape = CircleShape,
    val itemTextColor: Color = TextColor,
    val itemTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),
    val iconContainerColor: Color = IconContainerColor,

    val dialogContainerColor: Color = DialogContainerColor,

    val dialogTitle: String = "Choose an item",
    val dialogTitleColor: Color = TextColor,
    val dialogTitleStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),


    val audioListScreenConfig: AudioListScreenConfig = AudioListScreenConfig(),
    val multiMediaScreenConfig: MultiMediaScreenConfig = MultiMediaScreenConfig()
)

@Stable
data class MultiMediaScreenConfig(
    val confirmButtonContainerColor: Color = IconContainerColor,
    val confirmButtonShape: Shape = RoundedCornerShape(8.dp),
    val confirmButtonTextColor: Color = Color.White,
    val confirmButtonText: String = "Done",
    val confirmButtonTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),

    val checkBoxSelectedColor: Color = TextColor,
    val checkBoxUnSelectedColor: Color = Gray,
    val checkBoxUnCheckmarkColor: Color = Color.White,
)

@Stable
data class AudioListScreenConfig(
    val audioItemTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),
    val audioUnselectedItemColor: Color = Gray,
    val audioSelectedItemColor: Color = TextColor,

    val searchBoxTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),
    val searchBoxTextColor: Color = TextColor,
    val searchBoxHintText: String = "Search audio",
    val searchBoxIcon: Int = R.drawable.mahdiasd_ic_search,
    val searchBoxIconColor: Color = IconColor,

    val confirmButtonContainerColor: Color = IconContainerColor,
    val confirmButtonShape: Shape = RoundedCornerShape(8.dp),
    val confirmButtonTextColor: Color = Color.White,
    val confirmButtonText: String = "Done",
    val confirmButtonTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold),

    val checkBoxSelectedColor: Color = TextColor,
    val checkBoxUnSelectedColor: Color = Gray,
    val checkBoxUnCheckmarkColor: Color = Color.White,
)

fun PickerConfig.getTitle(pickerType: PickerType): String {
    return when (pickerType) {
        PickerType.Audio -> audioTitle
        PickerType.Storage -> storageTitle
        PickerType.ImageAndVideo -> imageAndVideoTitle
        PickerType.ImageOnly -> imageTitle
        PickerType.VideoOnly -> videoTitle
    }
}

fun PickerConfig.getIcon(pickerType: PickerType): Int {
    return when (pickerType) {
        PickerType.Audio -> audioIcon
        PickerType.Storage -> storageIcon
        PickerType.ImageAndVideo -> imageVideoIcon
        PickerType.ImageOnly -> imageIcon
        PickerType.VideoOnly -> videoIcon
    }
}