package kz.batana.intranet_v4.ui.teacher_page.courses

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.COURSES
import kz.batana.intranet_v4.data.Entities.Course

class TeacherCoursesInteractor(private val presenter: TeacherCoursesMVP.Presenter) : TeacherCoursesMVP.Interactor {

    override fun getCourseList() {
        val teacherId = firebaseAuth.currentUser!!.uid
        databaseReference.child(COURSES).child(teacherId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list: ArrayList<Course> = ArrayList()
                var idList: ArrayList<String> = ArrayList()
                for (it in dataSnapshot.children) {
                    val course = it.getValue(Course::class.java)
                    list.add(course!!)
                    idList.add(it.key!!)
                }
                presenter.receivedStudentList(list, idList)
            }

        })
    }


}