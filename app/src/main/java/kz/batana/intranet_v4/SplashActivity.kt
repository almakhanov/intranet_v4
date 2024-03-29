package kz.batana.intranet_v4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.ui.admin_page.AdminMainActivity
import kz.batana.intranet_v4.ui.sign_in.SignInActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(firebaseAuth.currentUser != null){
            openActivityByCurrentUser()
        }else{
            roleOfUser = AppConstants.ANONYMOUS
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

    }


    private fun openActivityByCurrentUser(){
        val currentUserId = firebaseAuth.currentUser!!.uid

        var appContext = this
        log("currentUserId=$currentUserId")

        var studentItemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(student in dataSnapshot.children){
                    log("studentUserId="+student.value)
                    if(student.key.toString() == currentUserId) {
                        log("studentUserId=================")
                        roleOfUser = STUDENT
                        startActivity(Intent(appContext, StudentMainActivity::class.java))
                        finish()
                        return
                    }
                }
            }
        }
        databaseReference.child(AppConstants.STUDENTS).addListenerForSingleValueEvent(studentItemListener)



        var teacherItemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(teacher in dataSnapshot.children){
                    log("teacherUserId="+teacher.value)
                    if(teacher.key.toString() == currentUserId) {
                        log("teacherUserId=================")
                        roleOfUser = TEACHER
                        startActivity(Intent(appContext, TeacherMainActivity::class.java))
                        finish()
                        return
                    }
                }
            }
        }
        databaseReference.child(AppConstants.TEACHERS).addListenerForSingleValueEvent(teacherItemListener)


        var adminItemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(admin in dataSnapshot.children){
                    log("adminUserId="+admin.value)
                    if(admin.key.toString() == currentUserId) {
                        log("adminUserId=================")
                        roleOfUser = ADMIN
                        startActivity(Intent(appContext, AdminMainActivity::class.java))
                        finish()
                        return
                    }
                }
            }
        }
        databaseReference.child(AppConstants.ADMINS).addListenerForSingleValueEvent(adminItemListener)
    }
}
