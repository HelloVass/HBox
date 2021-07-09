package info.hellovass.hbox

import android.os.Bundle
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TextView(this).post {

        }

        Looper.getMainLooper().setMessageLogging {

        }
        listOf<Int>().takeLast(1)
    }
}