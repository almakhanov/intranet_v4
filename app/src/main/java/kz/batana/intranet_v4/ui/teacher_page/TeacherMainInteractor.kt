package kz.batana.intranet_v4.ui.teacher_page

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.COURSE_STUDENTS
import kz.batana.intranet_v4.AppConstants.MARKS
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Student

class TeacherMainInteractor(private val presenter: TeacherMainMVP.Presenter) : TeacherMainMVP.Interactor {

    override fun getCourseStudentsList(courseId: String) {
        databaseReference.child(COURSE_STUDENTS).child(courseId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var studentIds: ArrayList<String> = ArrayList()
                for(it in dataSnapshot.children){
                    studentIds.add(it.getValue(String::class.java)!!)
                }
                getStudentsData(studentIds)
            }

        })
    }

    private fun getStudentsData(studentIds: ArrayList<String>){
        for(id in studentIds){
            databaseReference.child(STUDENTS).child(id).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    log(databaseError.toString())
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    log("|||||||"+dataSnapshot.value.toString())
                    var student = dataSnapshot.getValue(Student::class.java)
                    presenter.sendStudentData(student!!, dataSnapshot.key!!)
                }

            })
        }

    }

    override fun putMark(markValue: Int, studentId: String, courseId: String) {
        val markKey = databaseReference.child(MARKS).child(studentId).push().key.toString()
        val mark = Mark(markValue, courseId)
        databaseReference.child(MARKS).child(studentId).child(markKey).setValue(mark)
        presenter.onSuccessMessage("Success!")
    }

}