package kz.batana.intranet_v4.ui.student_page.profile


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainMVP


class StudentProfileFragment : Fragment() {

    private var listener: StudentMainMVP.StudentProfileFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = StudentProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_profile, container, false)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is StudentMainMVP.StudentProfileFragmentListener) {
            listener = context as StudentMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherProfileFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}
