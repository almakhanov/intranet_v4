package kz.batana.intranet_v4

import kz.batana.intranet_v4.ui.sign_in.SignInContract
import kz.batana.intranet_v4.ui.sign_in.SignInPresenter
import kz.batana.intranet_v4.ui.sign_in.SignInRepository
import kz.batana.intranet_v4.ui.sign_in.sign_up.SignUpContract
import kz.batana.intranet_v4.ui.sign_in.sign_up.SignUpPresenter
import kz.batana.intranet_v4.ui.sign_in.sign_up.SignUpRepository
import org.koin.dsl.module.module


val appModule = module {

    factory{ (view: SignInContract.View) -> SignInPresenter(get(), view) as SignInContract.Presenter }

    single { SignInRepository() as SignInContract.Repository }


    factory{ (view: SignUpContract.View) -> SignUpPresenter(get(), view) as SignUpContract.Presenter }

    single { SignUpRepository() as SignUpContract.Repository }


    factory { (context: SignUpContract.StudentFragmentListener) -> context}
}