package kz.batana.intranet_v4.ui.sign_in

import kz.batana.intranet_v4.App.Companion.firebaseAuth

class SignInInteractor(private val presenter: SignInPresenter) : SignInMVP.Interactor {

    override fun authorize(username: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener{
                    if(it.isSuccessful) presenter.onSuccess()
                    else presenter.onFailed(it.exception!!)
                }
    }

}