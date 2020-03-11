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
        holder.bind(pokemonList[position])
    }

    class PokemonViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val image = view.pokemon_image
        val name = view.pokemon_name
        val principalType = view.principal_type
        val secondadaryType = view.secondary_type

        fun bind(item: Pokemon){
            image.loadImage((item.sprites as LinkedTreeMap<String, String>)["front_default"], getProgressDrawable(view.context))
            name.text = "${item.id} - ${item.name}"
            with((item.types as ArrayList<Any>)) {
                when(this.size){
                    0-> {}
                    1-> {
                        principalType.text = ((this[0] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                        secondadaryType.visibility = View.GONE
                    }
                    else ->  {
                        principalType.text = ((this[0] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                        secondadaryType.text = ((this[1] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
                    }
                }
            }

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