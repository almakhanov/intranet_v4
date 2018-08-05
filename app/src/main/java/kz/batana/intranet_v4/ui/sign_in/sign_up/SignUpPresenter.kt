package kz.batana.intranet_v4.ui.sign_in.sign_up

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kz.batana.intranet_v4.App.Companion.roleOfUser
import kz.batana.intranet_v4.AppConstants.ADMIN
import kz.batana.intranet_v4.AppConstants.ADMIN_SECRET_CODE
import kz.batana.intranet_v4.AppConstants.STUDENT
import kz.batana.intranet_v4.AppConstants.TEACHER
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.data.Entities.User

class SignUpPresenter(private val view: SignUpMVP.View) : SignUpMVP.Presenter {

    private val interactor = SignUpInteractor(this)
    private var newStudent = Student()
    private var newAdmin = Admin()
    private var newTeacher = Teacher()
    private var newUser = User()

    override fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                                  confirmPassword: String) {
        when {
            name.isEmpty() -> view.msg("Name is empty!")
            age.isEmpty() -> view.msg("Age is empty!")
            yearOfStudy.isEmpty() -> view.msg("Year of study is empty!")
            username.isEmpty() -> view.msg("Username is empty!")
            password.isEmpty() -> view.msg("Password is empty!")
            confirmPassword.isEmpty() -> view.msg("Confirm Password is empty!")
            password != confirmPassword -> view.msg("Passwords do not match!")
            else -> {
                newStudent = Student(name, age.toInt(), yearOfStudy.toInt())
                newUser = User(username, password)
                interactor.registerUserIntoFirebaseAuth(newUser)
            }
        }
    }

    override fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        when {
            name.isEmpty() -> view.msg("Name is empty!")
            age.isEmpty() -> view.msg("Age is empty!")
            username.isEmpty() -> view.msg("Username is empty!")
            password.isEmpty() -> view.msg("Password is empty!")
            confirmPassword.isEmpty() -> view.msg("Confirm Password is empty!")
            secretCode.isEmpty() -> view.msg("Secret Code is empty!")
            password != confirmPassword -> view.msg("Passwords do not match!")
            secretCode != ADMIN_SECRET_CODE -> view.msg("Incorrect secret code!")
            else -> {
                newAdmin = Admin(name, age.toInt())
                newUser = User(username, password)
                interactor.registerUserIntoFirebaseAuth(newUser)
            }
        }
    }

    override fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        when {
            name.isEmpty() -> view.msg("Name is empty!")
            age.isEmpty() -> view.msg("Age is empty!")
            degree.isEmpty() -> view.msg("Degree is empty!")
            username.isEmpty() -> view.msg("Username is empty!")
            password.isEmpty() -> view.msg("Password is empty!")
            confirmPassword.isEmpty() -> view.msg("Confirm Password is empty!")
            secretCode.isEmpty() -> view.msg("Secret Code is empty!")
            password != confirmPassword -> view.msg("Passwords do not match!")
            secretCode != ADMIN_SECRET_CODE -> view.msg("Incorrect secret code!")
            else -> {
                newTeacher = Teacher(name, age.toInt(), degree)
                newUser = User(username, password)
                interactor.registerUserIntoFirebaseAuth(newUser)
            }
        }
    }



    override fun onSuccess() {

        when(view.getCurrentSignUpper()){
            STUDENT -> {
                roleOfUser = STUDENT
                interactor.saveNewStudent(newStudent)
            }
            TEACHER -> {
                roleOfUser = TEACHER
                interactor.saveNewTeacher(newTeacher)
            }
            ADMIN -> {
                roleOfUser = ADMIN
                interactor.saveNewAdmin(newAdmin)
            }
        }
        view.onSuccess()

    }

    override fun onFailed(exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> view.msg("The Password Is Invalid, Please Try Valid Password!")
            is FirebaseAuthInvalidUserException -> view.msg("Incorrect email address!")
            is FirebaseNetworkException -> view.msg("Please Check Your Connection")
            is FirebaseAuthUserCollisionException -> view.msg("Such user is already exist!")
            else -> view.msg(exception.localizedMessage.toString())
        }
    }



}