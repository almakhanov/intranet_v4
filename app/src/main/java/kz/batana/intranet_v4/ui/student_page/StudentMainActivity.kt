package kz.batana.intranet_v4.ui.student_page

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_student_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.student_page.courses.StudentCoursesFragment
import kz.batana.intranet_v4.ui.student_page.profile.StudentProfileFragment

class StudentMainActivity : AppCompatActivity(), StudentMainMVP.View, StudentMainMVP.StudentProfileFragmentListener,
        NavigationView.OnNavigationItemSelectedListener, StudentMainMVP.StudentCoursesFragmentListener {

    private val presenter: StudentMainMVP.Presenter by lazy { StudentMainPresenter(this) }
    private var actionbar: ActionBar? = null
    private lateinit var studentProfileFragment: StudentProfileFragment
    private lateinit var studentCoursesFragment: StudentCoursesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)

        //toolbar
        setSupportActionBar(toolbar_student_main)
        actionbar = supportActionBar

        //nav animation
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_student, toolbar_student_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_student.addDrawerListener(toggle)
        toggle.syncState()

        //navigation view
        navigation_view_student_main.setNavigationItemSelectedListener(this)

        //default page
        actionbar?.apply {
            this.title = "Profile"
        }
        studentProfileFragment = StudentProfileFragment.newInstance()
        createFragment(studentProfileFragment, R.id.container_student_main)


//        text_student_main.text = firebaseAuth.currentUser!!.email
//
//        logout.setOnClickListener{
//            firebaseAuth.signOut()
//            startActivity(Intent(this, SignInActivity::class.java))
//            App.roleOfUser = AppConstants.ANONYMOUS
//            finish()
//        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_student_profile -> {
                actionbar?.apply {
                    this.title = "Profile"
                }

                studentProfileFragment = StudentProfileFragment.newInstance()
                createFragment(studentProfileFragment, R.id.container_student_main)
            }
            R.id.navigation_student_course -> {
                actionbar?.apply {
                    this.title = "Courses"
                }

                studentCoursesFragment = StudentCoursesFragment.newInstance()
                createFragment(studentCoursesFragment, R.id.container_student_main)
            }
            R.id.navigation_student_about -> {
                //TODO information in dialog
            }
            R.id.navigation_student_logout -> {
                firebaseAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                App.roleOfUser = AppConstants.ANONYMOUS
                finish()
            }

        }
        drawer_layout_student.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout_student.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout_student.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_student.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun createFragment(fragment: Fragment, layoutContainer: Int) {
        supportFragmentManager.beginTransaction()
                .replace(layoutContainer, fragment)
                //.addToBackStack(null)
                .commit()
    }
}
