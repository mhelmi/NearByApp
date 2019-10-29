package com.github.mhelmi.nearby.general

import android.os.AsyncTask
import com.github.mhelmi.nearby.general.db.NearbyRoomDatabase

class DeleteAllDatabaseTablesAsyncTask constructor(
    private val db: NearbyRoomDatabase
) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {
        db.clearAllTables()
        return null
    }
}