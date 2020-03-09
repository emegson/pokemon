package com.github.emerson.pokemon.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class PokemonListResponse(
    val count: Int=0,
    val next: String = "",
    val previous: String = "",
    val results: ArrayList<PokemonRequest>? = arrayListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readArrayList(List::class.java.classLoader) as ArrayList<PokemonRequest>?
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeString(next)
        parcel.writeString(previous)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonListResponse> {
        override fun createFromParcel(parcel: Parcel): PokemonListResponse {
            return PokemonListResponse(parcel)
        }

        override fun newArray(size: Int): Array<PokemonListResponse?> {
            return arrayOfNulls(size)
        }
    }
}
