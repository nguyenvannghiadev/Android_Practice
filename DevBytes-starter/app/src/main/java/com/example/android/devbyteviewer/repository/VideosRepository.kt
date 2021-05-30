package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.DevByteVideo
import com.example.android.devbyteviewer.network.DevByteNetwork
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository for fetching devbyte videos from network and storing them on disk
 */

class VideosRepository(private val database: VideosDatabase){

	val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()){
		it.asDomainModel()
	}

	/**
	 * Refesh the videos stored in the offline cach.
	 * This function use the IO dispatcher to ensure the database insert database opration
	 * happens on the IO dispatcher.By switching to the IO dispatcher using withContext this
	 * function is now safe to call from any thread including the Main thread
	 */

	suspend fun refeshVideos() {
		withContext(Dispatchers.IO){
			Timber.d(">>>>>>refresh videos is called");
			val playlist = DevByteNetwork.devbytes.getPlaylist()
			database.videoDao.insertAll(playlist.asDatabaseModel())
		}

	}

}
