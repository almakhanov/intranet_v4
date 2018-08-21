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
import kz.batana.intranet_v4.AppConstants.TEACHER_SECRET_CODE
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.data.Entities.User
import org.koin.standalone.KoinComponent

class SignUpPresenter(private val repository: SignUpContract.Repository, override var view: SignUpContract.View?)
    : SignUpContract.Presenter, KoinComponent {

    private var newStudent = Student()
    private var newAdmin = Admin()
    private var newTeacher = Teacher()
    private var newUser = User()

    override fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                                  confirmPassword: String) {
        when {
            name.isEmpty() -> view?.msg("Name is empty!")
            age.isEmpty() -> view?.msg("Age is empty!")
            yearOfStudy.isEmpty() -> view?.msg("Year of study is empty!")
            username.isEmpty() -> view?.msg("Username is empty!")
            password.isEmpty() -> view?.msg("Password is empty!")
            confirmPassword.isEmpty() -> view?.msg("Confirm Password is empty!")
            password != confirmPassword -> view?.msg("Passwords do not match!")
            else -> {
                newStudent = Student(name, age.toInt(), yearOfStudy.toInt())
                newUser = User(username, password)
                repository.registerUserIntoFirebaseAuth(newUser, this)
            }
        }
    }

    override fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        when {
            name.isEmpty() -> view?.msg("Name is empty!")
            age.isEmpty() -> view?.msg("Age is empty!")
            username.isEmpty() -> view?.msg("Username is empty!")
            password.isEmpty() -> view?.msg("Password is empty!")
            confirmPassword.isEmpty() -> view?.msg("Confirm Password is empty!")
            secretCode.isEmpty() -> view?.msg("Secret Code is empty!")
            password != confirmPassword -> view?.msg("Passwords do not match!")
            secretCode != ADMIN_SECRET_CODE -> view?.msg("Incorrect secret code!")
            else -> {
                newAdmin = Admin(name, age.toInt())
                newUser = User(username, password)
                repository.registerUserIntoFirebaseAuth(newUser, this)
            }
        }
    }

    override fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        when {
            name.isEmpty() -> view?.msg("Name is empty!")
            age.isEmpty() -> view?.msg("Age is empty!")
            degree.isEmpty() -> view?.msg("Degree is empty!")
            username.isEmpty() -> view?.msg("Username is empty!")
            password.isEmpty() -> view?.msg("Password is empty!")
            confirmPassword.isEmpty() -> view?.msg("Confirm Password is empty!")
            secretCode.isEmpty() -> view?.msg("Secret Code is empty!")
            password != confirmPassword -> view?.msg("Passwords do not match!")
            secretCode != TEACHER_SECRET_CODE -> view?.msg("Incorrect secret code!")
            else -> {
                newTeacher = Teacher(name, age.toInt(), degree)
                newUser = User(username, password)
                repository.registerUserIntoFirebaseAuth(newUser, this)
            }
        }
    }



    override fun onSuccess() {

        when(view?.getCurrentSignUpper()){
            STUDENT -> {
                roleOfUser = STUDENT
                repository.saveNewStudent(newStudent,this)
            }
            TEACHER -> {
                roleOfUser = TEACHER
                repository.saveNewTeacher(newTeacher, this)
            }
            ADMIN -> {
                roleOfUser = ADMIN
                repository.saveNewAdmin(newAdmin, this)
            }
        }
        view?.onSuccess()

    }

    override fun onFailed(exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> view?.msg("The Password Is Invalid, Please Try Valid Password!")
            is FirebaseAuthInvalidUserException -> view?.msg("Incorrect email address!")
            is FirebaseNetworkException -> view?.msg("Please Check Your Connection")
            is FirebaseAuthUserCollisionException -> view?.msg("Such user is already exist!")
            else -> view?.msg(exception.localizedMessage.toString())
        }
    }



}