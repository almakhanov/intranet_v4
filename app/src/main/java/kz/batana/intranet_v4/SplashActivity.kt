package kz.batana.intranet_v4

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (firebaseAuth.currentUser != null) startActivity(Intent(this, StudentMainActivity::class.java))
        else startActivity(Intent(this, SignInActivity::class.java))

        finish()
    }
}
