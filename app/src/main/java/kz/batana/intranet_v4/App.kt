package kz.batana.intranet_v4

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kz.batana.intranet_v4.AppConstants.ANONYMOUS
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.AppConstants.TEACHERS

class App : Application() {

    companion object {
        lateinit var databaseReference : DatabaseReference
        lateinit var firebaseAuth: FirebaseAuth
        var roleOfUser = ANONYMOUS

        fun log(message: String) {
            Log.d("accepted", message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("App is loading")
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        //if(firebaseAuth.currentUser != null)
            roleOfUser = getRoleOfUser()
    }

    private fun getRoleOfUser(): String{
        val currentUserId = firebaseAuth.currentUser!!.uid

        var role = "norole"


        var studentItemListener: ValueEventListener = object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(student in dataSnapshot.children){
                    log(student.value.toString())
                    log(student.key.toString())
                    if(student.key.toString() == currentUserId) {
                        role = STUDENT
                        log("tadam")
                    }
                }
            }
        }
        databaseReference.child(STUDENTS).addListenerForSingleValueEvent(studentItemListener)



        var teacherItemListener: ValueEventListener = object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(teacher in dataSnapshot.children){
                    log(teacher.value.toString())
                    log(teacher.key.toString())
                    if(teacher.key.toString() == currentUserId) {
                        role = TEACHER
                        log("tadam")
                    }
                }
            }
        }
        databaseReference.child(TEACHERS).addListenerForSingleValueEvent(teacherItemListener)


        return role
    }
}