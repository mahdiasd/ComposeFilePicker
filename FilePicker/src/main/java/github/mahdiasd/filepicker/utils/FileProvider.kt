package github.mahdiasd.filepicker.utils

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

object FileProvider {

    fun getImage(context: Context, enableCamera: Boolean): ImmutableList<PickerFile> {
        val list = mutableSetOf<PickerFile>()
        if (enableCamera) {
            list.add(PickerFile("show camera"))
        }
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)

        val orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC"

        val cursor: Cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            orderBy
        ) ?: return listOf<PickerFile>().toImmutableList()

        while (cursor.moveToNext()) {
            val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            list.add(PickerFile(cursor.getString(dataColumnIndex)))
        }

        cursor.close()
        return list.toImmutableList()
    }

    fun getAudio(context: Context): ImmutableList<PickerFile> {
        val list = mutableSetOf<PickerFile>()
        val columns = arrayOf(
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.DATA,
        )
        val orderBy = MediaStore.Audio.AudioColumns.DATE_ADDED + " DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            orderBy
        ) ?: return listOf<PickerFile>().toImmutableList()

        while (cursor.moveToNext()) {
            val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            list.add(PickerFile(cursor.getString(dataColumnIndex)))
        }
        cursor.close()
        Log.d("TAG", "getAudio: $list")
        return list.toImmutableList()
    }

    fun getVideo(context: Context): ImmutableList<PickerFile> {
        val list = mutableSetOf<PickerFile>()
        val columns = arrayOf(
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns._ID
        )

        val orderBy = MediaStore.Video.VideoColumns.DATE_ADDED + " DESC"

        val cursor: Cursor = context.contentResolver.query(
            /* uri = */ MediaStore.Video.Media.EXTERNAL_CONTENT_URI, /* projection = */ columns, /* selection = */ null,
            /* selectionArgs = */ null, /* sortOrder = */ orderBy
        ) ?: return listOf<PickerFile>().toImmutableList()

        while (cursor.moveToNext()) {
            val dataColumnIndex: Int =
                cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            list.add(PickerFile(cursor.getString(dataColumnIndex)))
        }
        cursor.close()
        return list.toImmutableList()
    }

}