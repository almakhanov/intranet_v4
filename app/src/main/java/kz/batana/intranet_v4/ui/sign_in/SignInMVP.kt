package kz.batana.intranet_v4.ui.sign_in

interface SignInMVP {
    interface View{
        fun msg(message: String)
        fun openUserProfile(userType: String)
    }

    interface Presenter{
        fun login(username: String, password: String)
        fun onFailed(exception: Exception)
        fun openAcitvity(userType: String)
    }

    interface Interactor {
        fun authorize(username: String, password: String)
    }
}