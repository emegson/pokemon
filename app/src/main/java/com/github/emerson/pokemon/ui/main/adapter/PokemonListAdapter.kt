package com.github.emerson.pokemon.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.emerson.pokemon.R
import com.github.emerson.pokemon.data.model.Pokemon
import com.github.emerson.pokemon.ui.main.fragment.ListFragmentDirections
import com.github.emerson.pokemon.util.getProgressDrawable
import com.github.emerson.pokemon.util.loadImage
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonListAdapter(private val pokemonList: ArrayList<Pokemon>) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    fun updateDataSet(newList: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_item, parent, false) as ButtonMaterialCardView
        return PokemonViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    class PokemonViewHolder(var view: MaterialCardView) : RecyclerView.ViewHolder(view) {
        val image = view.pokemon_image
        val name = view.pokemon_name
        val principalType = view.principal_type
        val secondaryType = view.secondary_type

        fun bind(item: Pokemon) {
            var principal: String? = null
            var secondary: String? = null

            image.loadImage(
                item.getImage(),
                getProgressDrawable(view.context)
            )
            name.text = "${item.id} - ${item.name}"
            with((item.types as ArrayList<Any>)) {
                when (this.size) {
                    0 -> {
                    }
                    1 -> {
                        principal = item.getPrincipalType()
                        secondaryType.visibility = View.GONE
                    }
                    else -> {
                        principal = item.getPrincipalType()
                        secondary = item.getSecondaryType()

                    }
                }
            }
            principalType.text = principal
            secondaryType.text = secondary

            val firstTypeAccesibilityText =
                principal?.let {
                    view.context.getString(R.string.firstTypeAccesibility, it)
                }
            val secondTypeAccesibilityText =
                secondary?.let {
                    view.context.getString(R.string.secondTypeAccesibility, it)
                }
            view.context.getString(R.string.secondTypeAccesibility) ?: null
            view.contentDescription =
                "Pokemon ${item.name} ${firstTypeAccesibilityText} ${secondTypeAccesibilityText}"
            view.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    ListFragmentDirections.actionListFragmentToDetailFragment(
                        item
                    )
                )
            }
        }
    }
}