package kz.batana.intranet_v4.ui.teacher_page


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_teacher_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.teacher_page.courses.TeacherCoursesFragment
import kz.batana.intranet_v4.ui.teacher_page.profile.TeacherProfileFragment

class TeacherMainActivity : AppCompatActivity(), TeacherMainMVP.Presenter, NavigationView.OnNavigationItemSelectedListener, TeacherMainMVP.TeacherProfileFragmentListener, TeacherMainMVP.TeacherCoursesFragmentListener {

    private var actionbar: ActionBar? = null
    private lateinit var teacherProfileFragment: TeacherProfileFragment
    private lateinit var teacherCoursesFragment: TeacherCoursesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_main)

        //toolbar
        setSupportActionBar(toolbar_teacher_main)
        actionbar = supportActionBar

        //nav animation
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout_teacher, toolbar_teacher_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout_teacher.addDrawerListener(toggle)
        toggle.syncState()

        //navigation view
        navigation_view_teacher_main.setNavigationItemSelectedListener(this)

        //default page
        actionbar?.apply {
            this.title = "Profile"
        }
        teacherProfileFragment = TeacherProfileFragment.newInstance()
        createFragment(teacherProfileFragment, R.id.container_teacher_main)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_teacher_profile -> {
                actionbar?.apply {
                    this.title = "Profile"
                }

                teacherProfileFragment = TeacherProfileFragment.newInstance()
                createFragment(teacherProfileFragment, R.id.container_teacher_main)
            }
            R.id.navigation_teacher_course -> {
                actionbar?.apply {
                    this.title = "Courses"
                }

                teacherCoursesFragment = TeacherCoursesFragment.newInstance()
                createFragment(teacherCoursesFragment, R.id.container_teacher_main)
            }
            R.id.navigation_teacher_about -> {
                //TODO information in dialog
            }
            R.id.navigation_teacher_logout -> {
                firebaseAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                App.roleOfUser = AppConstants.ANONYMOUS
                finish()
            }

        }
        drawer_layout_teacher.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout_teacher.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout_teacher.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_teacher.closeDrawer(GravityCompat.START)
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
