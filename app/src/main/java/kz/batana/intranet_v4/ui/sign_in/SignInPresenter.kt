package kz.batana.intranet_v4.ui.sign_in

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class SignInPresenter(private val view : SignInMVP.View): SignInMVP.Presenter{


    private val interactor = SignInInteractor(this)

    override fun login(username: String, password: String) {
        if(username.isEmpty() || password.isEmpty()) view.msg("Fill the all fields!")
        else interactor.authorize(username, password)
    }

    override fun onSuccess() {
        view.onSuccess()
    }

    override fun onFailed(exception: Exception) {
        if (exception is FirebaseAuthInvalidCredentialsException) {
            view.msg("Incorrect username or password")
        } else view.msg(exception.message.toString())
    }
}