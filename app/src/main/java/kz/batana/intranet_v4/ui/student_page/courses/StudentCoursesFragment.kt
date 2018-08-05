package kz.batana.intranet_v4.ui.student_page.courses


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.ui.student_page.StudentMainActivity
import kz.batana.intranet_v4.ui.student_page.StudentMainMVP


class StudentCoursesFragment : Fragment(), StudentCoursesMVP.View {

    private val presenter: StudentCoursesMVP.Presenter by lazy { StudentCoursesPresenter(this) }
    private var listener: StudentMainMVP.StudentCoursesFragmentListener? = null

    companion object {
        @JvmStatic
        fun newInstance() = StudentCoursesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_courses, container, false)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is StudentMainMVP.StudentCoursesFragmentListener) {
            listener = context as StudentMainActivity
        } else {
            throw RuntimeException(context.toString() + " must implement TeacherCoursesFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
