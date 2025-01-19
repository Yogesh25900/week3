package com.example.week3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week3.R
import com.example.week3.adapter.PostAdapter
import com.example.week3.model.PostModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var postsList: MutableList<PostModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize the list and adapter
        postsList = mutableListOf()
        postAdapter = PostAdapter(requireContext(), postsList)

        // RecyclerView setup
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postAdapter

        // Fetch posts from Firebase
        fetchPostsFromFirebase()

        return view
    }

    private fun fetchPostsFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Posts")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postsList.clear() // Clear the previous list
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(PostModel::class.java)
                    post?.let {
                        postsList.add(it)
                    }
                }
                postAdapter.notifyDataSetChanged() // Notify adapter to update the UI
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load posts", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
