package kz.batana.intranet_v4.ui.sign_in.sign_up

import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.User

interface SignUpMVP {

    interface View{
        fun msg(message: String)
        fun onSuccess()
    }

    interface StudentFragmentListener{
        fun checkStudentData(name: String, age: Int, yearOfStudy: Int, username: String, password: String, confirmPassword: String)
    }

    interface TeacherFragmentListener{

    }

    interface AdminFragmentListener{

    }

    interface Presenter{
        fun checkStudentData(name: String, age: Int, yearOfStudy: Int, username: String, password: String, confirmPassword: String)
        fun onSuccess()
        fun onFailed(exception: Exception)
    }

    interface Interactor {
        fun saveNewStudent(student: Student)
        fun registerUsername(user: User)
    }
}