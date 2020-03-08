package com.github.emerson.pokemon.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.emerson.pokemon.R
import com.github.emerson.pokemon.data.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonListAdapter(private val pokemonList: ArrayList<Pokemon>) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    fun updateDataSet(newList: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
//        holder.view.pokemon_image.draw = pokemonList[position].imageUrl
        holder.view.pokemon_name.text = pokemonList[position].name
        holder.view.principal_type.text = pokemonList[position].principalType
        holder.view.secondary_type.text = pokemonList[position].secondaryType

        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToDetailFragment(pokemonList[position]))
        }
    }

    class PokemonViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
}