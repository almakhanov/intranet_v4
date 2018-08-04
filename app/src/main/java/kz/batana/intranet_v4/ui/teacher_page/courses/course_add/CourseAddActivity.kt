package kz.batana.intranet_v4.ui.teacher_page.courses.course_add

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_course_add.*
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.AppConstants.COURSES
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Course

class CourseAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_add)

        //Toolbar
        setSupportActionBar(toolbar_teacher_add_course)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            this.title = "New Course registration"
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_teacher_add_course, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var name = edit_text_teacher_add_course_name.text.toString()
        var year = edit_text_teacher_add_course_year.text.toString()
        var credit = edit_text_teacher_add_course_credit.text.toString()
        return when (item.itemId) {
            R.id.item_teacher_add_course -> {
                if(name.isEmpty() || year.isEmpty() || credit.isEmpty()){
                    message("Fill the all fields!")
                }else {
                    val teacherId = firebaseAuth.currentUser!!.uid
                    val courseId = databaseReference.child(COURSES).child(teacherId).push().key.toString()
                    databaseReference.child(COURSES).child(teacherId).child(courseId).setValue(Course(name, year.toInt(), credit.toInt()))
                    finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun message(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
