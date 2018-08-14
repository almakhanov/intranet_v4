package kz.batana.intranet_v4.ui.sign_in

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER

class SignInRepository : SignInContract.Repository {

    override fun authorize(username: String, password: String, presenter: SignInContract.Presenter) {
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener{
                    if(it.isSuccessful) openActivityByCurrentUser(presenter)
                    else presenter.onFailed(it.exception!!)
                }
    }

    private fun openActivityByCurrentUser(presenter: SignInContract.Presenter){
        val currentUserId = firebaseAuth.currentUser!!.uid

        log("currentUserId=$currentUserId")

        var studentItemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                log(databaseError.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(student in dataSnapshot.children){
                    log("studentUserId=" + student.value)
                    if(student.key.toString() == currentUserId) {
                        log("studentUserId=================")
                        roleOfUser = AppConstants.STUDENT
                        presenter.openAcitvity(STUDENT)
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
                    log("teacherUserId=" + teacher.value)
                    if(teacher.key.toString() == currentUserId) {
                        log("teacherUserId=================")
                        roleOfUser = TEACHER
                        presenter.openAcitvity(TEACHER)
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
                    log("adminUserId=" + admin.value)
                    if(admin.key.toString() == currentUserId) {
                        log("adminUserId=================")
                        roleOfUser = ADMIN
                        presenter.openAcitvity(ADMIN)
                        return
                    }
                }
            }
        }
        databaseReference.child(AppConstants.ADMINS).addListenerForSingleValueEvent(adminItemListener)
    }

}