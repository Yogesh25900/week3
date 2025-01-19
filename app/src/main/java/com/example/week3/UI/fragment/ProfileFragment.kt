package com.example.week3.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.week3.R
import com.example.week3.databinding.ActivityNavBinding
import com.example.week3.databinding.FragmentProfileBinding
import com.example.week3.respository.UserRepositoryImp
import com.example.week3.viewmodel.UserViewModel


class ProfileFragment : Fragment() {

    lateinit var binding:FragmentProfileBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = FragmentProfileBinding.inflate(inflater,container,
            false)
        return  binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repo = UserRepositoryImp()
        userViewModel = UserViewModel(repo)

        var currentuser = userViewModel.getCurrentUser()
        currentuser.let{
            userViewModel.getUserFromDatabase(it?.uid.toString())


        }
        userViewModel._userData.observe(requireActivity()){
            binding.profileName.text = it?.firstname+" "+it?.lastname
            binding.profileEmail.text= it?.email

        }




    }


}