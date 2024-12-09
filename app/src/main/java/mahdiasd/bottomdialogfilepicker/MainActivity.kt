package mahdiasd.bottomdialogfilepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import mahdiasd.bottomdialogfilepicker.ui.theme.ComposeFilePickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeFilePickerTheme {
                PickerDialogSampleScreen()
            }
        }
    }
}

