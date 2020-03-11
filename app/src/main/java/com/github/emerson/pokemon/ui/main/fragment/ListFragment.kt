package com.github.emerson.pokemon.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.emerson.pokemon.R
import com.github.emerson.pokemon.ui.main.adapter.EndlessRecyclerViewScrollListener
import com.github.emerson.pokemon.ui.main.adapter.PokemonListAdapter
import com.github.emerson.pokemon.ui.main.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    companion object {
        var lastRequest: Int = 0
        fun newInstance() =
            ListFragment()
    }

    private lateinit var viewModel: PokemonViewModel
    private val pokemonListAdapter =
        PokemonListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)

        viewModel.refresh(lastRequest)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pokemon_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonListAdapter
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(this.layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    if (lastRequest < 100)
                        viewModel.refresh(lastRequest)
                }
            })
        }

        list_swipe_refresh.setOnRefreshListener {
            error_message.visibility = View.GONE
            if (lastRequest < 100) {
                viewModel.refresh(lastRequest)
                list_swipe_refresh.isRefreshing = true
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        configurePokemonLiveData()

        configureLoadingLiveData()

        configureErrorLiveData()
    }

    private fun configureErrorLiveData() {
        viewModel.loadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    list_swipe_refresh.isRefreshing = false
                    View.VISIBLE.apply {
                        error_message.visibility
                    }
                    View.GONE.apply {
                        pokemon_list.visibility
                    }
                } else {
                    View.VISIBLE.apply {
                        pokemon_list.visibility
                    }
                    View.GONE.apply {
                        error_message.visibility
                    }
                }
            }
        })
    }

    private fun configureLoadingLiveData() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                list_swipe_refresh.isRefreshing = it
            }
        })
    }

    private fun configurePokemonLiveData() {
        viewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer { pokemon ->
            pokemon?.let {
                pokemon_list.visibility = View.VISIBLE
                pokemonListAdapter.updateDataSet(pokemon)
                synchronized(lastRequest) {
                    lastRequest = pokemon.size
                }
            }
        })
    }


}
