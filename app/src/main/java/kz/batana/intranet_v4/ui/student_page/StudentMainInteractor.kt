package kz.batana.intranet_v4.ui.student_page

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.COURSES
import kz.batana.intranet_v4.AppConstants.COURSE_STUDENTS
import kz.batana.intranet_v4.AppConstants.STUDENT_COURSES
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
                    val idList: ArrayList<String> = ArrayList()
                    var list: ArrayList<Course> = ArrayList()
                    for(objs in it.children){
                        val course = objs.getValue(Course::class.java)
                        idList.add(objs.key!!)
                        list.add(course!!)
                    }
                    log("${idList.size}==${list.size}")
                    getTeacherObject(list, it.key.toString(), idList)
                }
            }

        })
    }

    private fun getTeacherObject(list: ArrayList<Course>, teacherId: String, idList: ArrayList<String>){
        databaseReference.child(TEACHERS).child(teacherId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                log("teacher dataSnap="+dataSnapshot.value.toString())
                val teacher = dataSnapshot.getValue(Teacher::class.java)
                presenter.sendCoursesListAndTeacher(list, teacher!!, idList)
            }

        })

    }

    override fun getCourseOwnList() {
        val studentId = firebaseAuth.currentUser!!.uid
        databaseReference.child(STUDENT_COURSES).child(studentId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var list: ArrayList<Course> = ArrayList()
                var listIds: ArrayList<String> = ArrayList()
                for (it in dataSnapshot.children){
                    val course = it.getValue(Course::class.java)
                    list.add(course!!)
                    listIds.add(it.key!!)

                }
                presenter.sendCourseOwnList(list,listIds)
            }

        })
    }

    override fun saveCourse(course: Course, courseId: String) {
        val studentId = App.firebaseAuth.currentUser!!.uid
        databaseReference.child(STUDENT_COURSES).child(studentId).child(courseId).setValue(course)
        val ukey = databaseReference.child(COURSE_STUDENTS).child(courseId).push().key.toString()
        databaseReference.child(COURSE_STUDENTS).child(courseId).child(ukey).setValue(studentId)
        presenter.msg("Success!")
    }

}