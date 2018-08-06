package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

class StudentMainPresenter(private val view: StudentMainMVP.View) : StudentMainMVP.Presenter {

    private val interactor = StudentMainInteractor(this)

    override fun getCourseListWithTeachers() {
        interactor.getCourseListWithTeachers()
    }

    override fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher) {
        view.sendCoursesListAndTeacher(list, teacher)
    }
}