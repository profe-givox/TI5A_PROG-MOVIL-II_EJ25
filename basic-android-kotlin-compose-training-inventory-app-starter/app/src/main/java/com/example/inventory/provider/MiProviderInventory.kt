package com.example.inventory.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.inventory.InventoryApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    /*
     * The calls to addURI() go here for all the content URI patterns that the provider
     * recognizes. For this snippet, only the calls for table 3 are shown.
     */

    /*
     * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
     * in the path.
     */
    addURI("com.example.inventory.provider", "inventory", 1)

    /*
     * Sets the code for a single row to 2. In this case, the # wildcard is
     * used. content://com.example.app.provider/table3/3 matches, but
     * content://com.example.app.provider/table3 doesn't.
     */
    addURI("com.example.inventory.provider", "inventory/#", 2)

    addURI("com.example.inventory.provider", "inventory/*", 2)
}

class MiProviderInventory (val ctx: Context) : ContentProvider() {
    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {

        val cursor = MatrixCursor(arrayOf("id","name","price","quantity"))
        val coroutineScope =CoroutineScope(Dispatchers.IO)

          when( sUriMatcher.match(p0)){
              1 -> {
                  val inventario =
                      (ctx as InventoryApplication).container.itemsRepository.getAllItemsStream()
                  val job = coroutineScope.launch {
                      withContext(Dispatchers.IO) {
                          inventario.collect {
                              it.forEach { item ->
                                  cursor.addRow(
                                      arrayOf(item.id, item.name, item.price, item.quantity)
                                  )
                              }

                          }
                      }
                  }
                  runBlocking { job.join() }

              }
              2 -> Unit
              3 -> Unit
          }

        return cursor
    }

    override fun getType(p0: Uri): String? {
        return when(sUriMatcher.match(p0)){
            1 -> "vnd.android.cursor.dir/vnd.com.example.inventory.provider.inventory"
            2 -> "vnd.android.cursor.item/vnd.com.example.inventory.provider.inventory"
            else -> ""
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}