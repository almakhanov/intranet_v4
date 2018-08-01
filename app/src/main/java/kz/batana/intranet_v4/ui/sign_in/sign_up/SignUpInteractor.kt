package kz.batana.intranet_v4.ui.sign_in.sign_up

import kz.batana.intranet_v4.App.Companion.databaseReference
import kz.batana.intranet_v4.App.Companion.firebaseAuth
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.AppConstants.STUDENTS
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.User


class SignUpInteractor(private val presenter: SignUpPresenter) : SignUpMVP.Interactor {



    override fun saveNewStudent(student: Student) {
        log("student is saving -> $student")
        var key = databaseReference.child(STUDENTS).push().key.toString()
        databaseReference.child(STUDENTS).child(key).setValue(student)
    }


    override fun registerUsername(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(user.username, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        presenter.onSuccess()
                    }
                    else {
                        presenter.onFailed(it.exception!!)
                    }
                }
    }

}