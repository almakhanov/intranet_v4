package kz.batana.intranet_v4.ui.sign_in.sign_up


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up_student.*
import kz.batana.intranet_v4.R

class SignUpStudentFragment : Fragment() {

    private var listener: SignUpContract.StudentFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = SignUpStudentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_student, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpContract.StudentFragmentListener) {
            listener = context as SignUpActivity
        } else {
            throw RuntimeException(context.toString() + " must implement StudentFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun getStudentData(){
        listener?.checkStudentData(edit_text_sign_up_student_name.text.toString(), edit_text_sign_up_student_age.text.toString(),
                edit_text_sign_up_student_year.text.toString(), edit_text_sign_up_student_username.text.toString(),
                edit_text_sign_up_student_password.text.toString(), edit_text_sign_up_student_confirm_password.text.toString())
    }

}
