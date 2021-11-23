package com.ticnes.sextas.flowerList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ticnes.sextas.*
import com.ticnes.sextas.R
import com.ticnes.sextas.addFlower.AddFlowerActivity
import com.ticnes.sextas.addFlower.FLOWER_DESCRIPTION
import com.ticnes.sextas.addFlower.FLOWER_NAME
import com.ticnes.sextas.data.Flower


const val FLOWER_ID = "flower id"

class FlowersListActivity : AppCompatActivity() {
    private val newFlowerActivityRequestCode = 1
    private val flowersListViewModel by viewModels<FlowersListViewModel> {
        FlowersListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Instantiates headerAdapter and flowersAdapter. Both adapters are added to concatAdapter.
        which displays the contents sequentially */
        val headerAdapter = HeaderAdapter()
        val flowersAdapter = FlowersAdapter { flower -> adapterOnClick(flower) }
        val concatAdapter = ConcatAdapter(headerAdapter, flowersAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        flowersListViewModel.flowersLiveData.observe(this, {
            it?.let {
                flowersAdapter.submitList(it as MutableList<Flower>)
                headerAdapter.updateFlowerCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(flower: Flower) {

        val opcao = flower.id.toInt()
        // Choosing which Intent to execute based on the listed items ID number
        val intent = when (opcao) {
           1 ->  Intent(this, FrutasActivity()::class.java)
            /*2 ->  Intent(this, VegetaisActivity()::class.java)
            3 ->  Intent(this, CereaisActivity()::class.java)
            4 ->  Intent(this, CarnesActivity()::class.java)
            5 ->  Intent(this, LegumesActivity()::class.java)
            6 ->  Intent(this, LeitesActivity()::class.java)
            7 ->  Intent(this, OleosActivity()::class.java)
            8 ->  Intent(this, BebidasActivity()::class.java)
            9 ->  Intent(this, ProdlimpezasActivity()::class.java)
            10 ->  Intent(this, HigienecriancaActivity()::class.java)
            11 ->  Intent(this, HigieneadultoActivity()::class.java)
            12 ->  Intent(this, PapellimpezaActivity()::class.java)*/
            else -> Intent(this, FrutasActivity()::class.java)
       }

        startActivity(intent)
    }

    /* Adds flower to flowerList when FAB is clicked. */
    private fun fabOnClick() {
        val intent = Intent(this, AddFlowerActivity::class.java)
        startActivityForResult(intent, newFlowerActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newFlowerActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val flowerName = data.getStringExtra(FLOWER_NAME)
                val flowerDescription = data.getStringExtra(FLOWER_DESCRIPTION)

                flowersListViewModel.insertFlower(flowerName, flowerDescription)
            }
        }
    }
}