package kz.batana.intranet_v4

import kz.batana.intranet_v4.ui.sign_in.SignInContract
import kz.batana.intranet_v4.ui.sign_in.SignInPresenter
import kz.batana.intranet_v4.ui.sign_in.SignInRepository
import org.koin.dsl.module.module


val appModule = module {

    factory{ (view: SignInContract.View) -> SignInPresenter(get(), view) as SignInContract.Presenter }

    single { SignInRepository() as SignInContract.Repository }


}