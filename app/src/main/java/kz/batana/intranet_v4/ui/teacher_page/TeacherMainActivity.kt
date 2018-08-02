package kz.batana.intranet_v4.ui.teacher_page

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_teacher_main.*
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.sign_in.SignInActivity

class TeacherMainActivity : AppCompatActivity(), TeacherMainMVP.Presenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_main)


        //navigation view
        navigationViewAdmin.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_teacher_logout -> {
                    firebaseAuth.signOut()
                    startActivity(Intent(this, SignInActivity::class.java))
                    App.roleOfUser = AppConstants.ANONYMOUS
                    finish()
                }
                R.id.navigation_teacher_course -> {

                }
                R.id.navigation_teacher_profile -> {

                }
            }
            drawer_layout_teacher.closeDrawers()
            true
        }

        //toolbar
        setSupportActionBar(toolbar_teacher_main_profile)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_menu)
            this.title = "Profile"
        }

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
}
