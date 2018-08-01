package kz.batana.intranet_v4.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kz.batana.intranet_v4.App.Companion.log
import kz.batana.intranet_v4.R
import kz.batana.intranet_v4.data.Entities.Student
import kz.batana.intranet_v4.data.Entities.Teacher

class ListActivity : AppCompatActivity(), ListAdapter.OnItemClickListener, ListMVP.View {

    private val presenter : ListPresenter by lazy{ ListPresenter(this) }
    private lateinit var arrayList : ArrayList<Any>
    private lateinit var studList : ArrayList<Student>
    private lateinit var teachList : ArrayList<Teacher>
    lateinit var adapter : ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        log("ListActivity")

        /****************************************
         * Firebase
         */

        studList = ArrayList()
        teachList = ArrayList()

//        saveObjects()

        //getObjects()


        /****************************************/



    }

//    private fun getObjects(){
//        val fbStudents = FirebaseDatabase.getInstance().getReference("students")
//        val fbTeachers = FirebaseDatabase.getInstance().getReference("teachers")
//
//        fbStudents.addValueEventListener(object: ValueEventListener{
//
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//                studList = ArrayList()
//                if(p0!!.exists()){
//                    log(p0.toString())
//                    for(it in p0.children){
//                        val student = it.getValue(Student::class.java)
//                        studList.add(student!!)
//                    }
//                    recycle()
//                }
//            }
//
//        })
//
//        fbTeachers.addValueEventListener(object: ValueEventListener{
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//                teachList = ArrayList()
//                if(p0!!.exists()){
//                    log(p0.toString())
//                    for(it in p0.children){
//                        val teacher = it.getValue(Teacher::class.java)
//                        teachList.add(teacher!!)
//                    }
//                    recycle()
//                }
//            }
//
//        })
//    }
//
//    private fun saveObjects(){
//        val fbStudents = FirebaseDatabase.getInstance().getReference("students")
//        val fbTeachers = FirebaseDatabase.getInstance().getReference("teachers")
//        val studId1 = fbStudents.push().key
//        studList.add(Student(studId1,"Gosha", 19, 3.02))
//        val studId2 = fbStudents.push().key
//        val studId3 = fbStudents.push().key
//        val studId4 = fbStudents.push().key
//
//        val stud = Student(studId1,"Gosha", 19, 3.02)
//
//
//        fbStudents.child(studId1).setValue(stud)
//        fbStudents.child(studId2).setValue(Student(studId2,"Masha", 29, 3.12))
//        fbStudents.child(studId3).setValue(Student(studId3,"Sasha", 39, 3.22))
//        fbStudents.child(studId4).setValue(Student(studId4,"Sergei", 49, 3.32))
//
//
//        val teachId1 = fbTeachers.push().key
//        val teachId2 = fbTeachers.push().key
//        val teachId3 = fbTeachers.push().key
//
//        fbTeachers.child(teachId1).setValue(Teacher(teachId1,"Pasha", "math"))
//        fbTeachers.child(teachId2).setValue(Teacher(teachId2,"Dasha", "proga"))
//        fbTeachers.child(teachId3).setValue(Teacher(teachId3,"John", "english"))
//    }
//
//
//
//
//    private fun recycle(){
//
//        log("s sz:${studList.size}")
//        log("t sz:${teachList.size}")
//        arrayList = ArrayList()
//        arrayList.apply {
//            add("Students")
//            addAll(studList)
//            add("Teachers")
//            addAll(teachList)
//        }
//
//
//
//
//        var layout = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//        recyclerViewUsersList?.layoutManager = layout
//        adapter = ListAdapter(arrayList, this)
//        recyclerViewUsersList?.adapter = adapter
//        adapter.notifyDataSetChanged()
//
//        log("arr list size = ${arrayList.size}")
//    }
//
//
    override fun onHeaderClicked(h: String) {
        log(h)
    }

    override fun onStudentClicked(s: Student) {
        log(s.toString())
    }

    override fun onTeacherClicked(t: Teacher) {
        log(t.toString())
    }

}
