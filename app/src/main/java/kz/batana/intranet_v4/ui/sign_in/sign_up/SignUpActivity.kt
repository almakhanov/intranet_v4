package kz.batana.intranet_v4.ui.sign_in.sign_up

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity

class SignUpActivity : AppCompatActivity(), SignUpMVP.View, SignUpMVP.StudentFragmentListener {


    private val presenter : SignUpPresenter by lazy{ SignUpPresenter(this) }

    private lateinit var studentsFragment: SignUpStudentFragment
    private lateinit var teacherFragment: SignUpTeacherFragment
    private lateinit var adminFragment: SignUpAdminFragment


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
                        studentsFragment.getStudentData()
                    }
                    TEACHER -> {

                    }
                    ADMIN -> {

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

    override fun onSuccess() {
        msg("Success!")
        startActivity(Intent(this, StudentMainActivity::class.java))
    }

    private fun getCurrentSignUpper(currentSignUpper: Int) : String{
        return when(currentSignUpper){
            0 -> STUDENT
            1 -> TEACHER
            else -> ADMIN
        }
    }
}
