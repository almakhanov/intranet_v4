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
import kz.batana.intranet_v4.AppConstants.MARKS
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.AppConstants.STUDENT_COURSES
import kz.batana.intranet_v4.AppConstants.TEACHERS
import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Mark
import kz.batana.intranet_v4.data.Entities.Student
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
                analyzeCourseTeacherData(list, teacher!!, idList)
            }

        })

    }

    private fun analyzeCourseTeacherData(list: ArrayList<Course>, teacher: Teacher, idList: ArrayList<String>){
        val studentId = firebaseAuth.currentUser!!.uid
        var courseOwnList: ArrayList<Course> = ArrayList()
        var courseOwnIdsList : ArrayList<String> = ArrayList()
        databaseReference.child(STUDENT_COURSES).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {

                for(course in datasnapShot.children){
                    courseOwnIdsList.add(course.key.toString())
                    courseOwnList.add(course.getValue(Course::class.java)!!)
                }
                presenter.sendAllCourseTeacherData(list, teacher, idList, courseOwnList, courseOwnIdsList)
            }

        })


    }

    override fun getStudentData() {
        val studentId = firebaseAuth.currentUser!!.uid
        databaseReference.child(STUDENTS).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {
                var student = datasnapShot.getValue(Student::class.java)
                presenter.sendStudentData(student!!)
            }

        })
    }

    override fun getProfileInfo() {
        var email = firebaseAuth.currentUser!!.email
        val studentId = firebaseAuth.currentUser!!.uid
        databaseReference.child(STUDENTS).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {
                var student = datasnapShot.getValue(Student::class.java)
                getProfileInfoGpa(student!!, email!!, studentId)
            }
        })
    }

    private fun getProfileInfoGpa(student: Student, email: String, studentId: String) {
        var studentCourses: ArrayList<Course> = ArrayList()
        var studentCoursesIds: ArrayList<String> = ArrayList()
        var studentMarkList: ArrayList<Int> = ArrayList()
        databaseReference.child(STUDENT_COURSES).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {
                for(it in datasnapShot.children){
                    studentCourses.add(it.getValue(Course::class.java)!!)
                    studentCoursesIds.add(it.key!!)
                    studentMarkList.add(0)
                }
                getProfileInfoMark(student, email, studentId, studentCourses, studentCoursesIds, studentMarkList)
            }

        })
    }

    private fun getProfileInfoMark(student: Student, email: String, studentId: String, studentCourses: ArrayList<Course>, studentCoursesIds: ArrayList<String>, studentMarkList: ArrayList<Int>) {
        databaseReference.child(MARKS).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {
                for(markDataSnap in datasnapShot.children){
                    var mark = markDataSnap.getValue(Mark::class.java)
                    var markId = markDataSnap.key
                    for((index1, courseId) in studentCoursesIds.withIndex()){
                        if(courseId == markId){
                            studentMarkList[index1] = mark!!.value
                        }
                    }
                }
                presenter.sendProfileInfo(student, email, studentId, studentCourses, studentCoursesIds, studentMarkList)

            }

        })
    }


    override fun getTranscriptData() {
        val studentId = firebaseAuth.currentUser!!.uid

        databaseReference.child(MARKS).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {
                var marks: ArrayList<Mark> = ArrayList()
                var courseIds : ArrayList<String> = ArrayList()
                for(mark in datasnapShot.children){
                    courseIds.add(mark.key.toString())
                    marks.add(mark.getValue(Mark::class.java)!!)
                }
                getCoursesData(marks, courseIds)
            }

        })
    }

    private fun getCoursesData(marks: ArrayList<Mark>, courseIds: ArrayList<String>) {
        val studentId = firebaseAuth.currentUser!!.uid
        var courseList: ArrayList<Course> = ArrayList()
        var courseIdsList : ArrayList<String> = ArrayList()
        databaseReference.child(STUDENT_COURSES).child(studentId).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                log(p0.message)
            }

            override fun onDataChange(datasnapShot: DataSnapshot) {

                for(course in datasnapShot.children){
                    courseIdsList.add(course.key.toString())
                    courseList.add(course.getValue(Course::class.java)!!)
                }
                presenter.sendTranscriptData(marks, courseIds, courseList, courseIdsList)
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