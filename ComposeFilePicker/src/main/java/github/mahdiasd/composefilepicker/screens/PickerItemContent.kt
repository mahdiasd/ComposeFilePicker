@file:OptIn(ExperimentalMaterial3Api::class)

package github.mahdiasd.composefilepicker.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import github.mahdiasd.composefilepicker.utils.PickerType
import github.mahdiasd.composefilepicker.utils.PickerConfig
import github.mahdiasd.composefilepicker.utils.getIcon
import github.mahdiasd.composefilepicker.utils.getTitle

@Composable
fun PickerItemContent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    types: ImmutableList<PickerType>,
    pickerConfig: PickerConfig,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.SpaceAround,
    onItemSelect: (PickerType) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = pickerConfig.dialogTitle,
            style = pickerConfig.dialogTitleStyle,
            color = pickerConfig.dialogTitleColor
        )

        LazyRow(
            modifier = modifier
                .heightIn(min = 100.dp)
                .clipToBounds()
                .padding(16.dp),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
        ) {
            items(items = types, key = { it.hashCode() })
            { pickerType ->
                PickerItem(
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemSelect(pickerType) }),
                    title = pickerConfig.getTitle(pickerType),
                    icon = pickerConfig.getIcon(pickerType),
                    pickerConfig = pickerConfig,
                )
            }
        }
    }
}

@Composable
internal fun PickerItem(
    modifier: Modifier,
    title: String,
    @DrawableRes icon: Int,
    pickerConfig: PickerConfig,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically)
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .background(pickerConfig.iconContainerColor, shape = pickerConfig.iconShape)
                .padding(12.dp),
            painter = painterResource(icon),
            contentDescription = title,
            colorFilter = ColorFilter.tint(pickerConfig.iconColor)
        )
        Text(
            text = title,
            style = pickerConfig.itemTextStyle,
            color = pickerConfig.itemTextColor
        )
    }
}
