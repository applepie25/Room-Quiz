package unj.cs.studentapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import unj.cs.studentapps.data.Student
import unj.cs.studentapps.data.StudentDatabase
import unj.cs.studentapps.data.StudentViewModel
import unj.cs.studentapps.databinding.FragmentStudentFormBinding

private const val ARG_UID = "argStudentId"
private const val ARG_NAME = "argStudentName"
private const val ARG_POS = "argPosition"
private const val ARG_ID = "arg_Id"

class StudentFormFragment : Fragment() {
    private var uidParam: String? = null
    private var nameParam: String? = null
    private var positionParam: Int? = null
    private var _idParam: Int? = null

    private var _binding: FragmentStudentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by viewModels {StudentViewModel.Factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uidParam = it.getString(ARG_UID)
            nameParam = it.getString(ARG_NAME)
            positionParam = it.getInt(ARG_POS)
            _idParam = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addStudentBtn = view.findViewById<Button>(R.id.addStudentButton)
        val idText = view.findViewById<TextInputEditText>(R.id.inputStudentId)
        val nameText = view.findViewById<TextInputEditText>(R.id.inputStudentName)

        val studentDao = StudentDatabase.getInstance(view.context).studentDao()

        if (positionParam!! >= 0) {
            addStudentBtn.text = view.context.resources.getString(R.string.edit_button_label)
        } else {
            addStudentBtn.text = view.context.resources.getString(R.string.add_button_label)
        }

        uidParam?.let {
            idText.setText(uidParam)
        }

        nameParam?.let {
            nameText.setText(nameParam)
        }

        addStudentBtn.setOnClickListener{
            val student = Student(idText.text.toString(), nameText.text.toString())
            lateinit var toastMessage: String
            uidParam?.let {
                nameParam?.let {
                    student._id = _idParam!!
                    viewModel.setStudent(student)
                    toastMessage = "${student.name} was Edited"
                }
            } ?: run {
                viewModel.addStudent(student)
                toastMessage = "${student.name} was Added"
            }

            val action =
                StudentFormFragmentDirections.actionStudentFormFragmentToStudentListFragment()
            view.findNavController().navigate(action)
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: Int, param4: Int) =
            StudentFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UID, param1)
                    putString(ARG_NAME, param2)
                    putInt(ARG_POS, param3)
                    putInt(ARG_ID, param4)
                }
            }
    }
}