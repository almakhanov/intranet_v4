package kz.batana.intranet_v4.ui.sign_in

import kz.batana.intranet_v4.BasePresenter
import kz.batana.intranet_v4.BaseView

interface SignInContract {

    interface View : BaseView<Presenter>{
        fun msg(message: String)
        fun openUserProfile(userType: String)
    }

    interface Presenter : BasePresenter<View>{
        fun login(username: String, password: String)
        fun onFailed(exception: Exception)
        fun openAcitvity(userType: String)
    }

    interface Repository {
        fun authorize(username: String, password: String, presenter: Presenter)
    }
}