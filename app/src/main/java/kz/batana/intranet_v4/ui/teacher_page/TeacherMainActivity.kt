package kz.batana.intranet_v4.ui.teacher_page

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_teacher_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity

class TeacherMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_main)

        text_teacher_main.text = firebaseAuth.currentUser!!.email

        logout_teacher.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            App.roleOfUser = AppConstants.ANONYMOUS
            finish()
        }
    }
}
