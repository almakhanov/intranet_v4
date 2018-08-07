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
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_teacher_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.teacher_page.courses.TeacherCoursesFragment
import kz.batana.intranet_v4.ui.teacher_page.courses.course_students.CourseStudentsFragment
import kz.batana.intranet_v4.ui.teacher_page.profile.TeacherProfileFragment

class TeacherMainActivity : AppCompatActivity(), TeacherMainMVP.View, NavigationView.OnNavigationItemSelectedListener,
        TeacherMainMVP.TeacherProfileFragmentListener, TeacherMainMVP.TeacherCoursesFragmentListener, TeacherMainMVP.CourseStudentsFragementListener {


    private var actionbar: ActionBar? = null
    private lateinit var teacherProfileFragment: TeacherProfileFragment
    private lateinit var teacherCoursesFragment: TeacherCoursesFragment
    private lateinit var courseStudentsFragment: CourseStudentsFragment
    private val presenter: TeacherMainMVP.Presenter by lazy {TeacherMainPresenter(this)}

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
        var headerView = navigation_view_teacher_main.getHeaderView(0)
        var navUsername = headerView.findViewById<TextView>(R.id.text_view_teacher_navigation_header_email)
        navUsername.text = firebaseAuth.currentUser!!.email

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
                .commit()
    }

    private fun createFragmentWithBackStack(fragment: Fragment, layoutContainer: Int) {
        supportFragmentManager.beginTransaction()
                .replace(layoutContainer, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun openCourseStudentsList(course: Course, courseId: String) {
        actionbar?.apply {
            this.title = "${course.name} group"
        }
        courseStudentsFragment = CourseStudentsFragment.newInstance(courseId)
        createFragmentWithBackStack(courseStudentsFragment, R.id.container_teacher_main)
    }


    override fun getCourseStudentsList(courseID: String) {
        presenter.getCourseStudentsListForFragment(courseID)
    }

    override fun sendStudentData(students: Student, studentsId: String) {
        courseStudentsFragment.putCoursesListIntoRecyclerView(students,studentsId)
    }

    override fun applyToolbarTitle(title: String) {
        actionbar?.apply {
            this.title = title
        }
    }

    override fun putMark(markValue: String, studentId: String, courseId: String) {
        presenter.putMark(markValue,studentId,courseId)
    }

    override fun msg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
