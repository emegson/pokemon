package com.github.emerson.pokemon.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.github.emerson.pokemon.R
import com.github.emerson.pokemon.ui.main.viewmodel.PokemonDetailViewModel
import com.github.emerson.pokemon.util.getProgressDrawable
import com.github.emerson.pokemon.util.loadImage
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: PokemonDetailViewModel by lazy {
        ViewModelProviders.of(this).get(PokemonDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureObserver()
    }

    private fun configureObserver() {
        viewModel.pokemon.observe(viewLifecycleOwner, Observer { pokemon ->
            pokemon.apply {
                detail_pokemon_name.text = name
                detail_pokemon_image.loadImage(getImage(), getProgressDrawable(this@DetailFragment.context!!))
                detail_pokemon_principal_type.text = getPrincipalType()
                detail_pokemon_secondary_type.text = getSecondaryType()
                detail_pokemon_weight.text = height.toString()
                detail_pokemon_height.text = weight.toString()
                detail_pokemon_name3.text = name
                detail_pokemon_name4.text = name
                detail_pokemon_name5.text = name
                detail_pokemon_name6.text = name
                detail_pokemon_name7.text = name
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(args.pokemon)
    }
}