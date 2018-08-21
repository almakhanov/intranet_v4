package kz.batana.intranet_v4.ui.sign_in.sign_up

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.admin_page.AdminMainActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent

class SignUpActivity : AppCompatActivity(), SignUpContract.View, SignUpContract.StudentFragmentListener,
        SignUpContract.AdminFragmentListener, SignUpContract.TeacherFragmentListener, KoinComponent {

    override val presenter : SignUpContract.Presenter by inject { parametersOf(this) }

    private lateinit var studentsFragment: SignUpStudentFragment
    private lateinit var teacherFragment: SignUpTeacherFragment
    private lateinit var adminFragment: SignUpAdminFragment

    private var currentSignUpper = STUDENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Toolbar
        setSupportActionBar(toolbar_sign_up)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            this.title = "Sign up as"
        }



        //View pager fragments
        val adapter = SignUpFragmentPagerAdapter(supportFragmentManager)
        studentsFragment = SignUpStudentFragment.newInstance()
        teacherFragment = SignUpTeacherFragment.newInstance()
        adminFragment = SignUpAdminFragment.newInstance()

        adapter.addFragment(studentsFragment, "Student")
        adapter.addFragment(teacherFragment, "Teacher")
        adapter.addFragment(adminFragment, "Admin")
        view_pager_sign_up.adapter = adapter
        tab_layout_sign_up.setupWithViewPager(view_pager_sign_up)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sign_up_menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_sign_up_done -> {
                when(getCurrentSignUpper(view_pager_sign_up.currentItem)){
                    STUDENT -> {
                        currentSignUpper = STUDENT
                        studentsFragment.getStudentData()
                    }
                    TEACHER -> {
                        currentSignUpper = TEACHER
                        teacherFragment.getTeacherData()
                    }
                    ADMIN -> {
                        currentSignUpper = ADMIN
                        adminFragment.getAdminData()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun msg(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String, confirmPassword: String) {
        presenter.checkStudentData(name, age, yearOfStudy, username, password, confirmPassword)
    }

    override fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        presenter.checkAdminData(name, age, username, password, confirmPassword, secretCode)
    }

    override fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        presenter.checkTeacherData(name, age, degree, username, password, confirmPassword, secretCode)
    }


    override fun onSuccess() {
        msg("Success!")
        when(roleOfUser){
            STUDENT -> {
                startActivity(Intent(this, StudentMainActivity::class.java))
            }
            TEACHER -> {
                startActivity(Intent(this, TeacherMainActivity::class.java))
            }
            ADMIN -> {
                startActivity(Intent(this, AdminMainActivity::class.java))
            }
        }
        finish()
    }

    private fun getCurrentSignUpper(currentSignUpper: Int) : String{
        return when(currentSignUpper){
            0 -> STUDENT
            1 -> TEACHER
            else -> ADMIN
        }
    }

    override fun getCurrentSignUpper() : String{
        return currentSignUpper
    }
}
