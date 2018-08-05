package kz.batana.intranet_v4.ui.student_page.courses

class StudentCoursesPresenter(private val view: StudentCoursesMVP.View) : StudentCoursesMVP.Presenter {
    private val interactor = StudentCoursesInteractor(this)
}