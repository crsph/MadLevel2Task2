package com.example.madlevel2task2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_question.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        questions.add(Question(getString(R.string.first_question), false))
        questions.add(Question(getString(R.string.second_question), true))
        questions.add(Question(getString(R.string.third_question), true))
        questions.add(Question(getString(R.string.fourth_question), true))

        binding.rvQuestions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter

        binding.rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelperLeft().attachToRecyclerView(rvQuestions)
        createItemTouchHelperRight().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelperLeft(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                println(questions[position].questionAnswer)
                if (!questions[position].questionAnswer) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {
                    questionAdapter.notifyDataSetChanged()
                    Snackbar.make(tvQuestion, getString(R.string.wrong), Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

        return ItemTouchHelper(callback)
    }

    private fun createItemTouchHelperRight(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                // Checks if the question is getting swiped towards the right direction.
                // If swipe right delete the viewHolder and update the adapter
                // Else update the adapter so that question can't be swiped
                if (questions[position].questionAnswer) {
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {
                    questionAdapter.notifyDataSetChanged()
                    Snackbar.make(tvQuestion, getString(R.string.wrong), Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

        return ItemTouchHelper(callback)
    }
}