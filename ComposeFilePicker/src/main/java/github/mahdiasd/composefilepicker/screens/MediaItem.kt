package github.mahdiasd.composefilepicker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.video.VideoFrameDecoder
import github.mahdiasd.composefilepicker.utils.PickerFile
import github.mahdiasd.composefilepicker.utils.PickerConfig
import java.io.File


@Composable
fun MediaItem(
    pickerFile: PickerFile,
    config: PickerConfig,
    isSelected : Boolean,
    onChecked: () -> Unit = {},
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(VideoFrameDecoder.Factory()) }
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build()
    }


    val painter = rememberAsyncImagePainter(model = pickerFile.path, imageLoader = imageLoader)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onChecked() }
            .background(Color.Gray, RoundedCornerShape(16.dp))
            .aspectRatio(1f)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable { onChecked() }
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )

        CircleCheckbox(
            modifier = Modifier.align(Alignment.TopEnd),
            selected = isSelected,
            selectedColor = config.multiMediaScreenConfig.checkBoxSelectedColor,
            unSelectedColor = config.multiMediaScreenConfig.checkBoxUnSelectedColor,
            iconSize = 32.dp,
            onChecked = onChecked
        )

        if (pickerFile.file.isVideo())
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                Modifier
                    .size(16.dp)
                    .shadow(2.dp, CircleShape, spotColor = config.iconColor)
                    .align(Alignment.Center)
                    .background(config.dialogContainerColor, CircleShape)
                    .padding(8.dp),
                tint = config.iconColor,
            )
    }
}

fun File.isVideo(): Boolean {
    val extension = this.extension.lowercase()
    return extension in setOf("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm", "mpeg", "mpg", "3gp", "m4v")
}


@Composable
internal fun CircleCheckbox(
    modifier: Modifier = Modifier,
    selected: Boolean,
    selectedColor: Color,
    unSelectedColor: Color,
    iconSize: Dp = 24.dp,
    onChecked: () -> Unit = {},
) {
    Icon(
        imageVector = Icons.Filled.Done,
        tint = Color.White,
        modifier = modifier
            .size(iconSize)
            .padding(4.dp)
            .size(24.dp)
            .shadow(0.dp, CircleShape)
            .background(if (selected) selectedColor else unSelectedColor, shape = CircleShape)
            .padding(4.dp)
            .clickable {
                onChecked()
            },
        contentDescription = "checkbox"
    )

}
