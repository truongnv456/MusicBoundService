package navigationcomponentturtorialcom.example.boundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicBoundService : Service() {
    private val mBinder = MyBinder()
    private var mMediaPlayer: MediaPlayer? = null

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class MyBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getMusicBoundService(): MusicBoundService = this@MusicBoundService
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("MusicBoundService", "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.e("MusicBoundService", "onBind")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("MusicBoundService", "onUnBind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.e("MusicBoundService", "onDestroy")
        super.onDestroy()
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    fun startMusic() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(applicationContext, R.raw.trentinhbanduoitinhyeu)
        }
        mMediaPlayer?.start()
    }
}