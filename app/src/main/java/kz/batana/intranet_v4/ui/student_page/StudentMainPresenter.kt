package kz.batana.intranet_v4.ui.student_page

class StudentMainPresenter(private val view: StudentMainMVP.View) : StudentMainMVP.Presenter {
    private val interactor = StudentMainInteractor(this)
}