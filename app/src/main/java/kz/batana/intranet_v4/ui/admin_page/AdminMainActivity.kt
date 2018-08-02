package kz.batana.intranet_v4.ui.admin_page

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_main.*
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants.ANONYMOUS
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity

class AdminMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)


        text_admin_main.text = firebaseAuth.currentUser!!.email

        logout_admin.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            roleOfUser = ANONYMOUS
            finish()
        }
    }
}
