package kz.batana.intranet_v4.ui.student_page

import kz.batana.intranet_v4.data.Entities.Course
import kz.batana.intranet_v4.data.Entities.Teacher

class StudentMainPresenter(private val view: StudentMainMVP.View) : StudentMainMVP.Presenter {

    private val interactor = StudentMainInteractor(this)

    override fun getCourseListWithTeachers() {
        interactor.getCourseListWithTeachers()
    }

    override fun sendCoursesListAndTeacher(list: ArrayList<Course>, teacher: Teacher, courseIdList: ArrayList<String>) {
        view.sendCoursesListAndTeacher(list, teacher, courseIdList)
    }

    override fun saveCourse(course: Course, teacher: Teacher, courseId: String) {
        interactor.saveCourse(course, courseId)
    }

    override fun msg(message: String) {
        view.msg(message)
    }

    override fun getCourseOwnList() {
        interactor.getCourseOwnList()
    }

    override fun sendCourseOwnList(list: ArrayList<Course>, listIds: ArrayList<String>) {
        view.sendCourseOwnList(list, listIds)
    }
}