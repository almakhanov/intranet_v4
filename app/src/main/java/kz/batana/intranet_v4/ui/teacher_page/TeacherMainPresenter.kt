package kz.batana.intranet_v4.ui.teacher_page

class TeacherMainPresenter(private val view: TeacherMainMVP.View) : TeacherMainMVP.Presenter {
    private val interactor = TeacherMainInteractor(this)


}