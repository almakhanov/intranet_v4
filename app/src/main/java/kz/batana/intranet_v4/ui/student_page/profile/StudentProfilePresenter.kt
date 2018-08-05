package kz.batana.intranet_v4.ui.student_page.profile

class StudentProfilePresenter(private val view: StudentProfileMVP.View) : StudentProfileMVP.Presenter {
    private val interactor = StudentProfileInteractor(this)
}