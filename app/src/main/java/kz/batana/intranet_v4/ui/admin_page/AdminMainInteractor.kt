package kz.batana.intranet_v4.ui.admin_page

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.ADMINS
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.AppConstants.TEACHERS
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class AdminMainInteractor(private val presenter: AdminMainMVP.Presenter) : AdminMainMVP.Interactor {

    override fun getStudentsList() {
        databaseReference.child(STUDENTS).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var studentsList: ArrayList<Student> = ArrayList()
                for(it in dataSnapshot.children){
                    val student = it.getValue(Student::class.java)
                    studentsList.add(student!!)
                }
                presenter.receivedStudentList(studentsList)
            }

        })
    }

    override fun getTeachersList() {
        databaseReference.child(TEACHERS).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var teachersList: ArrayList<Teacher> = ArrayList()
                for(it in dataSnapshot.children){
                    val teacher = it.getValue(Teacher::class.java)
                    teachersList.add(teacher!!)
                }
                presenter.receivedTeacherList(teachersList)
            }

        })
    }

    override fun getAdminsList() {
        databaseReference.child(ADMINS).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var adminsList: ArrayList<Admin> = ArrayList()
                for(it in dataSnapshot.children){
                    val admin = it.getValue(Admin::class.java)
                    adminsList.add(admin!!)
                }
                presenter.receivedAdminList(adminsList)
            }

        })
    }

}