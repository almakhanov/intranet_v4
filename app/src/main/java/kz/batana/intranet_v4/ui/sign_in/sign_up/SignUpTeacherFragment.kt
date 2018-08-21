package kz.batana.intranet_v4.ui.sign_in.sign_up


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sign_up_teacher.*
import kz.batana.intranet_v4.R


class SignUpTeacherFragment : Fragment() {

    private var listener: SignUpContract.TeacherFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = SignUpTeacherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_sign_up_teacher, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpContract.TeacherFragmentListener) {
            listener = context as SignUpActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun getTeacherData(){
        listener?.checkTeacherData(edit_text_sign_up_teacher_name.text.toString(), edit_text_sign_up_teacher_age.text.toString(),
                edit_text_sign_up_teacher_degree.text.toString(), edit_text_sign_up_teacher_username.text.toString(),
                edit_text_sign_up_teacher_password.text.toString(), edit_text_sign_up_teacher_confirm_password.text.toString(),
                edit_text_sign_up_teacher_secret_code.text.toString())
    }


}
