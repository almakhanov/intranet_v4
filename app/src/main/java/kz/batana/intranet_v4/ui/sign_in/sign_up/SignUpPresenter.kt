package kz.batana.intranet_v4.ui.sign_in.sign_up

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.User

class SignUpPresenter(private val view: SignUpMVP.View) : SignUpMVP.Presenter {


    private val interactor = SignUpInteractor(this)
    private var newStudent = Student()
    private var newUser = User()

    override fun checkStudentData(name: String, age: Int, yearOfStudy: Int, username: String, password: String,
                                  confirmPassword: String) {
        if(username.isEmpty() || name.isEmpty() || age <= 0 || yearOfStudy <= 0 || password.isEmpty() || confirmPassword.isEmpty()){
            view.msg("Fill the all fields!")
        }else if(password != confirmPassword){
            view.msg("Passwords do not match!")
        }else {
            newStudent = Student(name, age, yearOfStudy)
            newUser = User(username, password)
            interactor.registerUsername(newUser)
        }
    }

    override fun onSuccess() {
        view.onSuccess()
        interactor.saveNewStudent(newStudent)
    }

    override fun onFailed(exception: Exception) {
        if (exception is FirebaseAuthInvalidCredentialsException) {
            view.msg("Incorrect username or password")
        } else if (exception is FirebaseAuthUserCollisionException) {
            view.msg("Such user is already exist!")
        } else view.msg(exception.message.toString())
    }



}