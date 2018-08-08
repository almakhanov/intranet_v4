package kz.batana.intranet_v4.ui.student_page

import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.android.synthetic.main.activity_student_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.student_page.courses.StudentCoursesAllFragment
import kz.batana.intranet_v4.ui.student_page.courses.StudentCoursesOwnFragment
import kz.batana.intranet_v4.ui.student_page.profile.StudentProfileFragment

class StudentMainActivity : AppCompatActivity(), StudentMainMVP.View, StudentMainMVP.StudentProfileFragmentListener,
        NavigationView.OnNavigationItemSelectedListener, StudentMainMVP.StudentCoursesAllFragementListener, StudentMainMVP.StudentCoursesOwnFragementListener, StudentMainMVP.StudentTranscriptFragmentListener {



    private val presenter: StudentMainMVP.Presenter by lazy { StudentMainPresenter(this) }
    private var actionbar: ActionBar? = null
    private lateinit var studentProfileFragment: StudentProfileFragment
    private lateinit var studentCoursesOwnFragment: StudentCoursesOwnFragment
    private lateinit var studentCoursesAllFragment: StudentCoursesAllFragment
    private lateinit var currentStudent: Student


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

        var headerView = navigation_view_student_main.getHeaderView(0)
        var navUsername = headerView.findViewById<TextView>(R.id.text_view_student_navigation_header_email)
        navUsername.text = firebaseAuth.currentUser!!.email

        //default page
        actionbar?.apply {
            this.title = "Profile"
        }
        studentProfileFragment = StudentProfileFragment.newInstance()
        createFragment(studentProfileFragment, R.id.container_student_main)



        presenter.getStudentData()
    }

    override fun getProfileInfo() {
        presenter.getProfileInfo()
    }

    override fun sendStudentData(student: Student) {
        currentStudent = student
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
            R.id.navigation_student_course_own -> {
                actionbar?.apply {
                    this.title = "My Courses"
                }

                studentCoursesOwnFragment = StudentCoursesOwnFragment.newInstance()
                createFragment(studentCoursesOwnFragment, R.id.container_student_main)
            }
            R.id.navigation_student_course_all -> {
                actionbar?.apply {
                    this.title = "New Courses"
                }

                studentCoursesAllFragment = StudentCoursesAllFragment.newInstance()
                createFragment(studentCoursesAllFragment, R.id.container_student_main)
            }
            R.id.navigation_student_about -> {
                openAboutDialog()
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

    override fun sendTranscriptData(markList: ArrayList<Int>, ownCourseList: ArrayList<Course>, courseIds: ArrayList<String>) {
        studentCoursesOwnFragment.putCoursesListIntoRecyclerView(markList, ownCourseList,courseIds)
    }

    override fun getTranscriptData() {
        presenter.getTranscriptData()
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

    override fun getCourseListWithTeachers() {
        presenter.getCourseListWithTeachers()
    }

    override fun getCourseOwnList() {
        presenter.getTranscriptData()
    }

    override fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, courseIdList: ArrayList<String>) {
        studentCoursesAllFragment.putCoursesListIntoRecyclerView(list, teacher, courseIdList)
    }

    override fun msg(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun saveCourse(course: Course, teacher: Teacher, courseId: String) {
        presenter.saveCourse(course, teacher, courseId)
    }

    override fun sendCourseOwnList(list: ArrayList<Course>, idList: ArrayList<String>) {
        log(list.toString())
    }

    override fun sendStudentInfo(student: Student, email: String, gpa: String) {
        studentProfileFragment.sendStudentData(student, email, gpa)
    }

    private fun openAboutDialog(){
        var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("About")
        builder.setMessage("This is Intranet application for universities.\nMade by Nursultan Almakhanov\n2018\nVersion: 4.0.1")
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> msg("Thank you!")
            }
        }

        builder.setPositiveButton("OK", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

}
