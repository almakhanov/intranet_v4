package kz.batana.intranet_v4.ui.sign_in

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.koin.standalone.KoinComponent

class SignInPresenter(private val repository: SignInContract.Repository, override var view : SignInContract.View?)
    : SignInContract.Presenter, KoinComponent {

    override fun login(username: String, password: String) {
        when{
            username.isEmpty() -> view?.msg("Username is empty!")
            password.isEmpty() -> view?.msg("Password is empty!")
            else -> this.repository.authorize(username, password, this)
        }
    }


    override fun onFailed(exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> view?.msg("The Password Is Invalid, Please Try Valid Password!")
            is FirebaseAuthInvalidUserException -> view?.msg("Incorrect email address!")
            is FirebaseNetworkException -> view?.msg("Please Check Your Connection")
            else -> view?.msg(exception.localizedMessage.toString())
        }
    }

    override fun openAcitvity(userType: String) {
        view?.openUserProfile(userType)
    }
}