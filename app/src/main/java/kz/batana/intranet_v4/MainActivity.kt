package kz.batana.intranet_v4


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.ui.list.ListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, ListActivity::class.java))
        finish()
        log("MainActivity")

    }
}
