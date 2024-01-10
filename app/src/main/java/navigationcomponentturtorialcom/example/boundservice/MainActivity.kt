package navigationcomponentturtorialcom.example.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var mMusicBoundService: MusicBoundService? = null
    private var isServiceConnected: Boolean = false

    private var mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder: MusicBoundService.MyBinder = service as MusicBoundService.MyBinder
            mMusicBoundService = myBinder.getMusicBoundService()
            mMusicBoundService?.startMusic()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
            Log.e("ServiceConnected","False")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartService: Button = findViewById(R.id.btn_start_service)
        val btnStopService: Button = findViewById(R.id.btn_stop_service)

        btnStartService.setOnClickListener {
            onClickStartService()
        }

        btnStopService.setOnClickListener {
            onClickStopService()
        }
    }

    private fun onClickStartService() {
        val intent = Intent(this, MusicBoundService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun onClickStopService() {
        if (isServiceConnected) {
            unbindService(mServiceConnection)
            isServiceConnected = false
        }
    }
}