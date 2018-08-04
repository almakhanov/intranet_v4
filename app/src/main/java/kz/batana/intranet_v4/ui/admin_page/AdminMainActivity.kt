package kz.batana.intranet_v4.ui.admin_page

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_admin_main.*
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants.ANONYMOUS
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.sign_in.sign_up.SignUpFragmentPagerAdapter

class AdminMainActivity : AppCompatActivity(), AdminMainMVP.View, AdminMainMVP.StudentListFragmentListener,
        AdminMainMVP.TeacherListFragmentListener, AdminMainMVP.AdminListFragmentListener {

    private val presenter : AdminMainMVP.Presenter by lazy{ AdminMainPresenter(this) }

    private lateinit var studentsFragment: AdminMainStudentListFragment
    private lateinit var teacherFragment: AdminMainTeacherListFragment
    private lateinit var adminFragment: AdminMainAdminListFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)


        //Toolbar
        setSupportActionBar(toolbar_admin_main)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            this.title = "Users list"
        }


        //View pager fragments
        val adapter = SignUpFragmentPagerAdapter(supportFragmentManager)
        studentsFragment = AdminMainStudentListFragment.newInstance()
        teacherFragment = AdminMainTeacherListFragment.newInstance()
        adminFragment = AdminMainAdminListFragment.newInstance()

        adapter.addFragment(studentsFragment, "Students")
        adapter.addFragment(teacherFragment, "Teachers")
        adapter.addFragment(adminFragment, "Admins")
        view_pager_admin_main.adapter = adapter
        tab_layout_admin_main.setupWithViewPager(view_pager_admin_main)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_main_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_admin_sign_out -> {
                firebaseAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
                roleOfUser = ANONYMOUS
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun getStudentsList() {
        presenter.getStudentsList()
    }

    override fun receivedStudentList(studentsList: ArrayList<Any>) {
        studentsFragment.putStudentsListIntoRecyclerView(studentsList)
    }

    override fun receivedTeacherList(teacherList: ArrayList<Any>) {
        teacherFragment.putTeachersListIntoRecyclerView(teacherList)
    }

    override fun receivedAdminList(adminList: ArrayList<Any>) {
        adminFragment.putAdminsListIntoRecyclerView(adminList)
    }

    override fun getTeachersList() {
        presenter.getTeachersList()
    }

    override fun getAdminsList() {
        presenter.getAdminList()
    }
}
