package com.github.emerson.pokemon.data.model

import android.os.Parcel
import android.os.Parcelable

data class PokemonRequest (
    val name: String?,
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonRequest> {
        override fun createFromParcel(parcel: Parcel): PokemonRequest {
            return PokemonRequest(parcel)
        }

        override fun newArray(size: Int): Array<PokemonRequest?> {
            return arrayOfNulls(size)
        }
    }
}