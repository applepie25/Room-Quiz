package unj.cs.studentapps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import unj.cs.studentapps.databinding.FragmentStudentListBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import unj.cs.studentapps.data.StudentViewModel

private const val ARG_PARAM1 = "argToast"
class StudentListFragment : Fragment() {
    private var toastParam: String? = null

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var  studentRecyclerView: RecyclerView;
    private val viewModel : StudentViewModel by viewModels(){StudentViewModel.Factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            toastParam = it.getString(ARG_PARAM1)
        }

        studentAdapter = context?.let {
            StudentAdapter(viewModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentRecyclerView = binding.recyclerViewStudent
        studentRecyclerView.adapter = studentAdapter
        studentRecyclerView.layoutManager = LinearLayoutManager(context)

        studentRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        activity?.title = "Student List"

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener(){
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentFormFragment(null, null)
            view.findNavController().navigate(action)
        }
    }
    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            StudentListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
        var studentAdapter:StudentAdapter?=null
    }
}