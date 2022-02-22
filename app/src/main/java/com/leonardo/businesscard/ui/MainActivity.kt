package com.leonardo.businesscard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.leonardo.businesscard.App
import com.leonardo.businesscard.R
import com.leonardo.businesscard.databinding.ActivityMainBinding
import com.leonardo.businesscard.util.Image

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private val adapter by lazy { BusinessCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvCards.adapter = adapter
        getAllBusinessCard()
        insertListener()
    }

    private fun insertListener() {
        binding.fabAddNewCard.setOnClickListener {
            val intent = Intent(this, AddNewBusinessCard::class.java)
            startActivity(intent)
        }
        adapter.listenerShare = { card ->
            Image.share(this@MainActivity, card)
        }
        adapter.onLongClick = { id -> onLongClickItemRecyclerView(id = id) }
        //TODO Botão para apagar cards
        // binding.fabRemoveCard.setOnClickListener {
        //    deleteBusinessCard()
        //}
    }

    // TODO Botão para apagar cards
    private fun deleteBusinessCard(id: Int) {
        mainViewModel.delete(id)
        getAllBusinessCard()
    }

    private fun onLongClickItemRecyclerView(id: Int) {
        deleteBusinessCard(id)
        Toast.makeText(this, R.string.label_show_delete_success, Toast.LENGTH_SHORT).show()
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this) { businessCards ->
            adapter.submitList(businessCards)
        }
    }

}