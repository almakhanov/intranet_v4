package kz.batana.intranet_v4.ui.sign_in

interface SignInMVP {
    interface View{
        fun msg(message: String)
        fun onSuccess()
    }

    interface Presenter{
        fun login(username: String, password: String)
        fun onSuccess()
        fun onFailed(exception: Exception)
    }

    interface Interactor {
        fun authorize(username: String, password: String)
    }
}