package kz.batana.intranet_v4.ui.list

class ListPresenter(private val view : ListMVP.View) : ListMVP.Presenter {

    private val interactor = ListInteractor(this)

}