package com.github.emerson.pokemon.data.model

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(
    val id: Int = -1,
    val name: String = "noname",
    val principalType: String = "unknown",
    val secondaryType: String = "unknown",
    val imageUrl: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "noname",
        parcel.readString() ?: "unknown",
        parcel.readString() ?: "unknown",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(principalType)
        parcel.writeString(secondaryType)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}