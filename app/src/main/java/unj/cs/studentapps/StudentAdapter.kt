package unj.cs.studentapps

import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import unj.cs.studentapps.data.Student
import unj.cs.studentapps.data.StudentViewModel

class StudentAdapter(viewModel: StudentViewModel): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private val viewModel: StudentViewModel = viewModel
    init{
        viewModel.loadStudent()
        if(viewModel.studentList.value == null){
            viewModel._studentList.value = mutableListOf<Student>()
        }
    }

    class StudentViewHolder(val view:View): RecyclerView.ViewHolder(view){
        val nameTextView:TextView = view.findViewById<TextView>(R.id.nameTextView)
        val idTextView:TextView = view.findViewById<TextView>(R.id.idTextView)
    }

    override fun getItemCount(): Int {
        return viewModel.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_view_student, parent, false)

        return StudentViewHolder(layout)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //val context = holder.view.context
        val student: Student = viewModel.getStudent(position)
        val uid:String = student.uid
        val name:String = student.name
        val _id:Int = student._id
        holder.idTextView.text = uid
        holder.nameTextView.text = name
        holder.itemView.setOnClickListener(){
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentFormFragment( argPosition = position, argStudentId = uid, argStudentName = name, argId = _id)
            holder.view.findNavController().navigate(action)
        }
    }
}