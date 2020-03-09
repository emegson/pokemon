package com.github.emerson.pokemon.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.emerson.pokemon.R
import com.github.emerson.pokemon.data.model.Pokemon
import com.github.emerson.pokemon.ui.main.fragment.ListFragmentDirections
import com.google.gson.internal.LinkedTreeMap
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
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
//        holder.view.pokemon_image.draw = pokemonList[position].imageUrl
        holder.view.pokemon_name.text = "${pokemonList[position].id} - ${pokemonList[position].name}"
        with((pokemonList[position].types as ArrayList<Any>)) {
            when(this.size){
                0-> {}
                1-> {
                    holder.view.principal_type.text = ((this[0] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                    holder.view.secondary_type.visibility = View.GONE
                }
                else ->  {
                    holder.view.principal_type.text = ((this[0] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                    holder.view.secondary_type.text = ((this[1] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                }
            }
        }
        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    pokemonList[position]
                )
            )
        }
    }

    class PokemonViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }
}