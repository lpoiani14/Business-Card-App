package com.leonardo.businesscard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.leonardo.businesscard.App
import com.leonardo.businesscard.data.BusinessCard
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
        //TODO Botão para apagar cards
        // binding.fabRemoveCard.setOnClickListener {
        //    deleteBusinessCard()
        //}
    }

    // TODO Botão para apagar cards
    // private fun deleteBusinessCard(){
    //    mainViewModel.deleteAll()
    //    getAllBusinessCard()
    //}

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this) { businessCards ->
            adapter.submitList(businessCards)
        }
    }

}