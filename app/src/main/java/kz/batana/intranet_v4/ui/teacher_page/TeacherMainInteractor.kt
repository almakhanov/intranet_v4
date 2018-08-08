package kz.batana.intranet_v4.ui.teacher_page

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.COURSE_STUDENTS
import kz.batana.intranet_v4.AppConstants.MARKS
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.AppConstants.TEACHERS
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class TeacherMainInteractor(private val presenter: TeacherMainMVP.Presenter) : TeacherMainMVP.Interactor {

    private val teacherId = firebaseAuth.currentUser!!.uid

    override fun getCourseStudentsList(courseId: String) {
        databaseReference.child(COURSE_STUDENTS).child(courseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var studentIds: ArrayList<String> = ArrayList()
                for(it in dataSnapshot.children){
                    studentIds.add(it.getValue(String::class.java)!!)
                }
                getStudentsData(studentIds, courseId)
            }

        })
    }

    private fun getStudentsData(studentIds: ArrayList<String>, courseId: String){
        var studentsList: ArrayList<Student> = ArrayList()
        databaseReference.child(STUDENTS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for((index, obj) in studentIds.withIndex()){
                    for(it in dataSnapshot.children){
                        if(it.key == obj){
                            studentsList.add(it.getValue(Student::class.java)!!)
                        }
                    }
                }
                getStudentMarkData(studentIds, courseId, studentsList)
            }

        })

    }

    private fun getStudentMarkData(studentIds: ArrayList<String>, courseId: String, studentsList: ArrayList<Student>){
        var markList: ArrayList<Int> = ArrayList()
        for(it in studentsList){
            markList.add(0)
        }

        databaseReference.child(MARKS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for((ind1, val1) in studentIds.withIndex()){
                    for (it in dataSnapshot.children){
                        if (it.key == val1){
                            for(it2 in it.children){
                                if(it2.key == courseId){
                                    var mark = it2.getValue(Mark::class.java)
                                    markList[ind1] = mark!!.value
                                }
                            }
                        }
                    }
                }

                presenter.sendStudentData(studentIds, courseId, studentsList, markList)

            }

        })

    }

    override fun putMark(markValue: Int, studentId: String, courseId: String) {
        val mark = Mark(markValue, courseId)
        databaseReference.child(MARKS).child(studentId).child(courseId).setValue(mark)
        presenter.onSuccessMessage("Success!")
    }

    override fun getTeacherProfile() {
        databaseReference.child(TEACHERS).child(teacherId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var teacher = dataSnapshot.getValue(Teacher::class.java)
                presenter.sendTeacherInfo(teacher!!, firebaseAuth.currentUser!!.email!!)
            }

        })
    }

}