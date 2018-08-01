package kz.batana.intranet_v4.ui.sign_in.sign_up


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.batana.intranet_v4.R


class SignUpAdminFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SignUpAdminFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_admin, container, false)
    }



}
