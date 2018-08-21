package kz.batana.intranet_v4.ui.sign_in.sign_up


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up_admin.*
import kz.batana.intranet_v4.R


class SignUpAdminFragment : Fragment() {

    private var listener: SignUpContract.AdminFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = SignUpAdminFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_admin, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpContract.AdminFragmentListener) {
            listener = context as SignUpActivity
        } else {
            throw RuntimeException(context.toString() + " must implement AdminFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun getAdminData(){
        listener?.checkAdminData(edit_text_sign_up_admin_name.text.toString(), edit_text_sign_up_admin_age.text.toString(),
                edit_text_sign_up_admin_username.text.toString(), edit_text_sign_up_admin_password.text.toString(),
                edit_text_sign_up_admin_confirm_password.text.toString(), edit_text_sign_up_admin_secret_code.text.toString())
    }



}
