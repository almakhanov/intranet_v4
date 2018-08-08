package kz.batana.intranet_v4.ui.teacher_page

import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class TeacherMainPresenter(private val view: TeacherMainMVP.View) : TeacherMainMVP.Presenter {

    private val interactor = TeacherMainInteractor(this)



    override fun getTeacherProfile() {
        interactor.getTeacherProfile()
    }

    override fun sendTeacherInfo(teacher: Teacher, email: String) {
        view.sendTeacherInfo(teacher, email)
    }

    override fun sendStudentData(studentIds: ArrayList<String>, courseId: String, studentsList: ArrayList<Student>, markList: ArrayList<Int>) {
        view.sendStudentData(studentIds, courseId, studentsList, markList)
    }

    override fun getCourseStudentsListForFragment(courseID: String) {
        interactor.getCourseStudentsList(courseID)
    }

    override fun putMark(markValue: String, studentId: String, courseId: String) {
        if(markValue.toInt() in 0..100)
            interactor.putMark(markValue.toInt(), studentId, courseId)
        else
            view.msg("Invalid number!")
    }

    override fun onSuccessMessage(message: String) {
        view.msg(message)
    }
}