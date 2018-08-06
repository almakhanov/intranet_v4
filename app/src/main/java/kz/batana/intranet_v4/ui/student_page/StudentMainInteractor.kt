package kz.batana.intranet_v4.ui.student_page

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.COURSES
import kz.batana.intranet_v4.AppConstants.TEACHERS
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

class StudentMainInteractor(private val presenter: StudentMainMVP.Presenter) : StudentMainMVP.Interactor {

    override fun getCourseListWithTeachers() {
        databaseReference.child(COURSES).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                log("dataSnap="+dataSnapshot.toString())

                for(it in dataSnapshot.children){
                    var list: ArrayList<Course> = ArrayList()
                    for(objs in it.children){
                        val course = objs.getValue(Course::class.java)
                        list.add(course!!)
                    }
                    getTeacherObject(list, it.key.toString())
                }
            }

        })
    }

    private fun getTeacherObject(list: ArrayList<Course>, teacherId: String){
        databaseReference.child(TEACHERS).child(teacherId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                log("teacher dataSnap="+dataSnapshot.value.toString())
                val teacher = dataSnapshot.getValue(Teacher::class.java)
                presenter.sendCoursesListAndTeacher(list, teacher!!)
            }

        })

    }

}