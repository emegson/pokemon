package com.github.emerson.pokemon.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emerson.pokemon.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: PokemonViewModel
    private val pokemonListAdapter = PokemonListAdapter(arrayListOf())

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
        }

        list_swipe_refresh.setOnRefreshListener {
            error_message.visibility = View.GONE
            pokemon_list.visibility = View.GONE
            pokemon_list_loading.visibility = View.VISIBLE
            viewModel.refresh()
            list_swipe_refresh.isRefreshing = false
        }

        viewModel.refresh()

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
                    error_message.visibility = View.VISIBLE
                    pokemon_list_loading.visibility = View.GONE
                    pokemon_list.visibility = View.GONE
                } else {
                    error_message.visibility = View.GONE
                    pokemon_list.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun configureLoadingLiveData() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                pokemon_list_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    error_message.visibility = View.GONE
                    pokemon_list.visibility = View.GONE
                } else {
                    pokemon_list.visibility = View.VISIBLE
                }
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
