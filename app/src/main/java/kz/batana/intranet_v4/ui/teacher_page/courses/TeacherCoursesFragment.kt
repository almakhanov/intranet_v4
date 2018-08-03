package kz.batana.intranet_v4.ui.teacher_page.courses


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainActivity
import kz.batana.intranet_v4.ui.teacher_page.TeacherMainMVP


class TeacherCoursesFragment : Fragment(), TeacherCoursesMVP.View {

    private val presenter : TeacherCoursesPresenter by lazy{ TeacherCoursesPresenter(this) }
    private var listener: TeacherMainMVP.TeacherCoursesFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = TeacherCoursesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_courses, container, false)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeacherMainMVP.TeacherCoursesFragmentListener) {
            listener = context as TeacherMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherCoursesFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}
