package kz.batana.intranet_v4.ui.teacher_page.profile


import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_teacher_profile.*
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Teacher
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
        listener?.getTeacherProfile()
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

    override fun onStart() {
        super.onStart()
        loadOn()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun loadOn(){
        layout_teacher_profile_box1.visibility = ConstraintLayout.GONE
        layout_teacher_profile_box2.visibility = ConstraintLayout.GONE
        progress_bar_teacher_profile.visibility = ProgressBar.VISIBLE
    }

    private fun loadOff(){
        progress_bar_teacher_profile.visibility = ProgressBar.GONE
        layout_teacher_profile_box1.visibility = ConstraintLayout.VISIBLE
        layout_teacher_profile_box2.visibility = ConstraintLayout.VISIBLE
    }

    fun setTeacherInfo(teacher: Teacher, email: String) {
        text_view_teacher_profile_name.text = teacher.name
        text_view_teacher_profile_email.text = email
        text_view_teacher_profile_age_value.text = teacher.age.toString()
        text_view_teacher_profile_degree_value.text = teacher.degree

        loadOff()
    }


}
