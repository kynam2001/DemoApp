package com.example.demoapp

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import java.net.URI

class MyContentProvider : ContentProvider() {

    companion object{
        const val AUTHORITY = "com.example.demoapp.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/notes")

        private const val NOTES = 1
        private const val NOTE_ID = 2

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "notes", NOTES)
            addURI(AUTHORITY, "note/#", NOTE_ID)
        }
    }

    private lateinit var dbHelper: MyDatabaseHelper

    override fun onCreate(): Boolean {
        dbHelper = MyDatabaseHelper(context!!)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        return when(uriMatcher.match(uri)){
            NOTES -> db.delete("notes", selection, selectionArgs)
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                db.delete("notes", "_id=?", arrayOf(id.toString()))
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun getType(uri: Uri): String {
        return when(uriMatcher.match(uri)){
            NOTES -> "vnd.android.cursor.dir/vnd.$AUTHORITY.notes"
            NOTE_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.notes"
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val db = dbHelper.writableDatabase
        val id = db.insert("notes", null, values)
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        return when(uriMatcher.match(uri)){
            NOTES -> db.query("notes", projection, selection, selectionArgs, null, null, sortOrder)
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                db.query("notes", projection, "_id=?", arrayOf(id.toString()), null, null , sortOrder)
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = dbHelper.writableDatabase
        return when(uriMatcher.match(uri)){
            NOTES -> db.update("notes", values, selection, selectionArgs)
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                db.update("notes", values, "_id=?", arrayOf(id.toString()))
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }
}