package kz.batana.intranet_v4.ui.student_page

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_student_main.*
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity

class StudentMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)

        text_student_main.text = firebaseAuth.currentUser!!.email

        logout.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}
