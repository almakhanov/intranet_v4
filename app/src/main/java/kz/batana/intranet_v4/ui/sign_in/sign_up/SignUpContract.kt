package kz.batana.intranet_v4.ui.sign_in.sign_up

import kz.batana.intranet_v4.BasePresenter
import kz.batana.intranet_v4.BaseView
import kz.batana.intranet_v4.data.Entities.Admin
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher
import kz.batana.intranet_v4.data.Entities.User

interface SignUpContract {

    interface View: BaseView<Presenter> {
        fun msg(message: String)
        fun onSuccess()
        fun getCurrentSignUpper() : String
    }

    interface Presenter : BasePresenter<View> {
        fun onSuccess()
        fun onFailed(exception: Exception)
        fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String,
                           secretCode: String)
        fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                             confirmPassword: String)
        fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String,
                             confirmPassword: String, secretCode: String)
    }

    interface Repository {
        fun registerUserIntoFirebaseAuth(user: User, presenter: Presenter)
        fun saveNewStudent(student: Student, presenter: Presenter)
        fun saveNewAdmin(admin: Admin, presenter: Presenter)
        fun saveNewTeacher(teacher: Teacher, presenter: Presenter)
    }

    interface StudentFragmentListener{
        fun checkStudentData(name: String, age: String, yearOfStudy: String, username: String, password: String,
                             confirmPassword: String)
    }

    interface TeacherFragmentListener{
        fun checkTeacherData(name: String, age: String, degree: String, username: String, password: String,
                             confirmPassword: String, secretCode: String)

    }

    interface AdminFragmentListener{
        fun checkAdminData(name: String, age: String, username: String, password: String, confirmPassword: String,
                           secretCode: String)

    }
}