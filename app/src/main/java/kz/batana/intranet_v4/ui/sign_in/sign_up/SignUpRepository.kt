package kz.batana.intranet_v4.ui.sign_in.sign_up

import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.ADMINS
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.AppConstants.TEACHERS
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.data.Entities.User


class SignUpRepository : SignUpContract.Repository {

    private var currentAuthorizingUserUID = "currentAuthorizingUserUID"

    override fun registerUserIntoFirebaseAuth(user: User, presenter: SignUpContract.Presenter) {
        firebaseAuth.createUserWithEmailAndPassword(user.username, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        currentAuthorizingUserUID = it.result.user.uid
                        presenter.onSuccess()
                    }
                    else {
                        presenter.onFailed(it.exception!!)
                    }
                }
    }

    override fun saveNewStudent(student: Student, presenter: SignUpContract.Presenter) {
        log("student is saving -> $student")
        databaseReference.child(STUDENTS).child(currentAuthorizingUserUID).setValue(student)
    }

    override fun saveNewAdmin(admin: Admin, presenter: SignUpContract.Presenter) {
        log("admin is saving -> $admin")
        databaseReference.child(ADMINS).child(currentAuthorizingUserUID).setValue(admin)
    }

    override fun saveNewTeacher(teacher: Teacher, presenter: SignUpContract.Presenter) {
        log("teacher is saving -> $teacher")
        databaseReference.child(TEACHERS).child(currentAuthorizingUserUID).setValue(teacher)
    }


}