package kz.batana.intranet_v4.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.admin_page.AdminMainActivity
import kz.batana.intranet_v4.ui.sign_in.sign_up.SignUpActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent

class SignInActivity : AppCompatActivity(), SignInContract.View, KoinComponent {

    override val presenter : SignInContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        button_sign_up.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        button_login_sign_in.setOnClickListener{
            button_login_sign_in.isEnabled = false
            presenter.login(edit_text_sign_in_username.text.toString(), edit_text_sign_in_password.text.toString())
        }

    }

    override fun msg(message: String) {
        button_login_sign_in.isEnabled = true
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun openUserProfile(userType: String) {
        var userIntent: Intent? = null
        when(userType){
            STUDENT -> {
                userIntent = Intent(this, StudentMainActivity::class.java)
            }
            TEACHER -> {
                userIntent = Intent(this, TeacherMainActivity::class.java)
            }
            ADMIN -> {
                userIntent = Intent(this, AdminMainActivity::class.java)
            }
        }

        startActivity(userIntent)
        finish()
    }


}
