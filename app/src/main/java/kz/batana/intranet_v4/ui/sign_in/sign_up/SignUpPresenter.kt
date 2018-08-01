package kz.batana.intranet_v4.ui.sign_in.sign_up

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
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

class SignUpPresenter(private val view: SignUpMVP.View) : SignUpMVP.Presenter {

    private val interactor = SignUpInteractor(this)
    private var newStudent = Student()
    private var newAdmin = Admin()
    private var newTeacher = Teacher()
    private var newUser = User()

    override fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                                  confirmPassword: String) {
        if(username.isEmpty() || name.isEmpty() || age.isEmpty() || yearOfStudy.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            view.msg("Fill the all fields!")
        }else if(password != confirmPassword){
            view.msg("Passwords do not match!")
        }else {
            newStudent = Student(name, age.toInt(), yearOfStudy.toInt())
            newUser = User(username, password)
            interactor.registerUserIntoFirebaseAuth(newUser)
        }
    }

    override fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        if(username.isEmpty() || name.isEmpty() || age.isEmpty() || secretCode.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            view.msg("Fill the all fields!")
        }else if(password != confirmPassword){
            view.msg("Passwords do not match!")
        }else if(secretCode != ADMIN_SECRET_CODE){
            view.msg("Incorrect secret code!")
        } else {
            newAdmin = Admin(name, age.toInt())
            newUser = User(username, password)
            interactor.registerUserIntoFirebaseAuth(newUser)
        }
    }

    override fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String, confirmPassword: String, secretCode: String) {
        if(username.isEmpty() || name.isEmpty() || age.isEmpty() || degree.isEmpty() || secretCode.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            view.msg("Fill the all fields!")
        }else if(password != confirmPassword){
            view.msg("Passwords do not match!")
        }else if(secretCode != TEACHER_SECRET_CODE){
            view.msg("Incorrect secret code!")
        } else {
            newTeacher = Teacher(name, age.toInt(), degree)
            newUser = User(username, password)
            interactor.registerUserIntoFirebaseAuth(newUser)
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
            is FirebaseAuthInvalidCredentialsException -> view.msg("Incorrect username or password")
            is FirebaseAuthUserCollisionException -> view.msg("Such user is already exist!")
            else -> view.msg(exception.message.toString())
        }
    }



}