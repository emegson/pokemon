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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)

        pokemon_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokemonListAdapter
            addOnScrollListener(object: EndlessRecyclerViewScrollListener(this.layoutManager as LinearLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.refresh(totalItemsCount)
                }
            })
        }

        list_swipe_refresh.setOnRefreshListener {
            error_message.visibility = View.GONE
            pokemon_list.visibility = View.GONE
            viewModel.refresh(pokemonListAdapter.itemCount)

            list_swipe_refresh.isRefreshing = true
        }

        viewModel.refresh(0)

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
                error_message.visibility = if(it) View.VISIBLE else View.GONE
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
            }
        })
    }


}
