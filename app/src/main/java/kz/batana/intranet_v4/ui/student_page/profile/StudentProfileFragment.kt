package kz.batana.intranet_v4.ui.student_page.profile


import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_student_profile.*
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student
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

        listener?.getProfileInfo()


        return view
    }

    override fun onStart() {
        super.onStart()
        loadOn()
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

    fun sendStudentData(student: Student, email: String, gpa: String) {
        text_view_student_profile_email.text = email
        text_view_student_profile_name.text = student.name
        text_view_student_profile_gpa_value.text = gpa
        text_view_student_profile_year_value.text = student.yearOfStudy.toString()
        text_view_student_profile_age_value.text = student.age.toString()
        loadOff()
    }


    private fun loadOn(){
        layout_student_profile_box1.visibility = ConstraintLayout.GONE
        layout_student_profile_box2.visibility = ConstraintLayout.GONE
        progress_bar_student_profile.visibility = ProgressBar.VISIBLE
    }

    private fun loadOff(){
        progress_bar_student_profile.visibility = ProgressBar.GONE
        layout_student_profile_box1.visibility = ConstraintLayout.VISIBLE
        layout_student_profile_box2.visibility = ConstraintLayout.VISIBLE
    }



}
