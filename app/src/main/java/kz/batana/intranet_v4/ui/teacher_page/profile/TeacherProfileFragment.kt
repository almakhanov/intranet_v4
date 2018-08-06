package kz.batana.intranet_v4.ui.teacher_page.profile


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainMVP


class TeacherProfileFragment : Fragment() {

    private var listener: TeacherMainMVP.TeacherProfileFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = TeacherProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_teacher_profile, container, false)


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeacherMainMVP.TeacherProfileFragmentListener) {
            listener = context as TeacherMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherProfileFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}
