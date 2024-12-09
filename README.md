# 📁 Jetpack Compose File Picker Library

Welcome to the **Jetpack Compose File Picker Library**! This library provides a customizable and easy-to-use file picker dialog for Jetpack Compose applications. It seamlessly supports Android versions from **API 21 (Lollipop)** to **API 34 (Android 14)**, handling different behaviors based on the Android version:

- For **Android 11 (API 30)** and above, it utilizes the modern **[Photo Picker Api](https://developer.android.com/training/data-storage/shared/photopicker)** for image and video selection.
- For devices below **Android 11**, it gracefully lists photos and videos within the dialog.

---

---

## 📱 Screenshots

| Image Picker Dialog                                                                                                                    | Audio Picker Screen                                                                                                                    |
|----------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| ![Image Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/Screenshot_20241209_110701_Media%20picker.jpg)   | ![Audio Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/Screenshot_20241209_110709_ComposeFilePicker.jpg) |
| ![Image Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/Screenshot_20241209_110730_ComposeFilePicker.jpg) | ![Audio Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/Screenshot_20241209_110743_ComposeFilePicker.jpg) |
| ![Image Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/Screenshot_20241209_110751_Media%20picker.jpg)   | ![Audio Picker](https://github.com/mahdiasd/ComposeFilePicker/blob/main/screenshots/images_below11.png)                              |

---

## 🌟 Features

- **Support for All Android Versions**: Compatible with Android 5.0 to Android 14.
- **Multiple File Types**: Pick images, videos, audio files, or any document from the storage.
- **Customizable UI**: Personalize the dialog's appearance with colors, icons, text styles, and shapes.
- **Modern Design**: Built with Jetpack Compose and Material Design guidelines.
- **Permission Handling**: Automatically requests required permissions.
- **Photo Picker Support**: Utilizes the latest Android Photo Picker when available.

---

## 🚀 Getting Started

### Installation

Add the dependency to your `build.gradle` file:

```groovy
dependencies {
  implementation("io.github.mahdiasd:ComposeFilePicker:1.0.6")
}
```

## ⚡ Quick Start Guide

Get up and running in just a few steps!

### Step 1: Define the Picker Types

Specify the types of files you want the user to pick.

```kotlin
val pickerTypes = persistentListOf(
    PickerType.ImageOnly,
    PickerType.VideoOnly,
    PickerType.Audio,
    PickerType.Storage
).toImmutableList()
```

### Step 2: Use the PickerDialog

Add the `PickerDialog` to your composable function.

```kotlin
@Composable
fun MyFilePicker(
    onFilesSelected: (ImmutableList<PickerFile>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Button to trigger the file picker
    Button(onClick = { showDialog = true }) {
        Text("Open File Picker")
    }

    // Display the picker dialog when showDialog is true
    if (showDialog) {
        PickerDialog(
            types = pickerTypes,
            pickerConfig = PickerConfig(maxSelection = 5),
            onDismiss = { showDialog = false },
            selected = { files ->
                onFilesSelected(files)
                showDialog = false
            }
        )
    }
}
```

### Step 3: Handle the Selected Files

Process the files returned by the picker.

```kotlin
val onFilesSelected: (ImmutableList<PickerFile>) -> Unit = { files ->
    // Handle the selected files here
    files.forEach { file ->
        Log.d("SelectedFile", "URI: ${file.uri}, Name: ${file.name}")
    }
}
```

That's it! You've integrated the file picker into your app.

---

## 🛠 Advanced Usage

For more control and customization, follow this guide.

### 1. Define the Picker Types

Specify the types of files you want the user to pick.

```kotlin
val pickerTypes = persistentListOf(
    PickerType.ImageOnly,
    PickerType.VideoOnly,
    PickerType.Audio,
    PickerType.Storage,
    PickerType.ImageAndVideo
).toImmutableList()
```

### 2. Configure the Picker

Customize the picker using `PickerConfig`.

```kotlin
val pickerConfig = PickerConfig(
    maxSelection = 5,
    imageTitle = "Select Images",
    videoTitle = "Select Videos",
    audioTitle = "Select Music",
    storageTitle = "Select Documents",
    imageAndVideoTitle = "Select Media",
    dialogTitle = "Choose File Type",
    iconColor = Color(0xFF1976D2),
    itemTextColor = Color.Black,
    dialogContainerColor = Color.White,
    iconShape = RoundedCornerShape(12.dp),
    itemTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    dialogTitleStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
)
```

### 3. Implement the PickerDialog

Use `PickerDialog` within your composable functions.

```kotlin
@Composable
fun MyCustomFilePicker(
    onFilesSelected: (ImmutableList<PickerFile>) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    // Button to trigger the file picker
    Button(onClick = { showDialog = true }) {
        Text("Open Custom File Picker")
    }

    // Display the picker dialog when showDialog is true
    if (showDialog) {
        PickerDialog(
            types = pickerTypes,
            pickerConfig = pickerConfig,
            onDismiss = { showDialog = false },
            selected = { files ->
                onFilesSelected(files)
                showDialog = false
            }
        )
    }
}
```

### 4. Handle the Selected Files

Process the files returned by the picker.

```kotlin
val onFilesSelected: (ImmutableList<PickerFile>) -> Unit = { files ->
    // Handle the selected files here
    files.forEach { file ->
        // Example: Display file name and URI
        Toast.makeText(context, "Selected: ${file.name}", Toast.LENGTH_SHORT).show()
    }
}
```

---

## 🎨 Customization Options

The library offers extensive customization to match your app's theme.

### PickerConfig Options

| Property                 | Description                                | Default Value                     |
|--------------------------|--------------------------------------------|-----------------------------------|
| `maxSelection`           | Maximum number of files to select          | `1`                               |
| `imageTitle`             | Title for image picker                     | `"Image"`                         |
| `videoTitle`             | Title for video picker                     | `"Video"`                         |
| `audioTitle`             | Title for audio picker                     | `"Audio"`                         |
| `storageTitle`           | Title for storage picker                   | `"Storage"`                       |
| `imageAndVideoTitle`     | Title for image & video picker             | `"Image & Video"`                 |
| `iconColor`              | Color of the icons                         | `IconColor`                       |
| `iconShape`              | Shape of the icon containers               | `CircleShape`                     |
| `itemTextColor`          | Color of the picker item texts             | `TextColor`                       |
| `itemTextStyle`          | Text style for the picker items            | `TextStyle(fontWeight = Bold)`    |
| `iconContainerColor`     | Background color of the icon containers    | `IconContainerColor`              |
| `dialogContainerColor`   | Background color of the dialog container   | `DialogContainerColor`            |
| `dialogTitle`            | Title of the picker dialog                 | `"Choose an item"`                |
| `dialogTitleColor`       | Color of the dialog title                  | `TextColor`                       |
| `dialogTitleStyle`       | Text style for the dialog title            | `TextStyle(fontWeight = Bold)`    |
| `audioListScreenConfig`  | Configuration for the audio list screen    | `AudioListScreenConfig()`         |
| `multiMediaScreenConfig` | Configuration for multimedia screen        | `MultiMediaScreenConfig()`        |

### Customizing Icons and Titles

You can provide custom icons and titles for different picker types.

```kotlin
val pickerConfig = PickerConfig(
    imageIcon = R.drawable.ic_custom_image,
    videoIcon = R.drawable.ic_custom_video,
    audioIcon = R.drawable.ic_custom_audio,
    storageIcon = R.drawable.ic_custom_storage,
    imageVideoIcon = R.drawable.ic_custom_media,
    imageTitle = "My Images",
    videoTitle = "My Videos",
    audioTitle = "My Music",
    storageTitle = "My Files",
    imageAndVideoTitle = "My Media"
)
```

### Styling the Dialog

Adjust the appearance of the dialog and its components.

```kotlin
val pickerConfig = PickerConfig(
    dialogTitle = "Select a File Type",
    dialogTitleColor = Color.DarkGray,
    dialogTitleStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
    dialogContainerColor = Color.LightGray
)
```



## 🔄 Supported Picker Types

- `PickerType.ImageOnly`: Pick images only.
- `PickerType.VideoOnly`: Pick videos only.
- `PickerType.Audio`: Pick audio files.
- `PickerType.Storage`: Pick any file from storage.
- `PickerType.ImageAndVideo`: Pick both images and videos.

---

## 💡 Additional Configurations

### AudioListScreenConfig

Customize the audio picker screen.

```kotlin
val audioConfig = AudioListScreenConfig(
    audioItemTextStyle = TextStyle(fontSize = 16.sp),
    audioUnselectedItemColor = Color.Gray,
    audioSelectedItemColor = Color.Blue,
    searchBoxHintText = "Search Music",
    confirmButtonText = "Select"
)
```

### MultiMediaScreenConfig

Customize the multimedia picker screen.

```kotlin
val mediaConfig = MultiMediaScreenConfig(
    confirmButtonText = "Finish",
    confirmButtonContainerColor = Color.Green,
    checkBoxSelectedColor = Color.Red
)
```

---

## 🔐 Permissions Handling

The library handles necessary permissions automatically based on the Android version:

- **Android 5.0 (API 21) to Android 10 (API 29)**:
  - Requests `Manifest.permission.READ_EXTERNAL_STORAGE`.
  - Displays photos and videos within the picker dialog.

- **Android 11 (API 30) and Above**:
  - Utilizes the **Photo Picker API**.
  - No need for storage permissions; operates within the scoped storage model.

---

## 📝 Notes

- **SEO-Friendly**: The library is designed with best practices to ensure discoverability.
- **Easy Integration**: Works out-of-the-box with minimal setup.
- **Full Control**: Offers advanced customization for professional needs.
- **Compatibility**: Tested and compatible with Android 5.0 (Lollipop) through Android 14.

---

## 🙏 Acknowledgements

Thank you for choosing the Jetpack Compose File Picker Library! We hope it simplifies file selection in your Compose applications.

---

## 📬 Contact

For questions, issues, or contributions, please open an issue on [GitHub](https://github.com/YourRepo/YourLibrary) or contact the maintainer at [your.email@example.com](mailto:your.email@example.com).

---

## 📄 License
```
Copyright 2025 Mahdi Asadollahpour composeFilePicker

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
---

Happy coding! 🎉
