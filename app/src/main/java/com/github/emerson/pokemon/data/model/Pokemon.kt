package com.github.emerson.pokemon.data.model

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(
    val id: Int = -1,
    val name: String = "noname",
    val types: Any,
    val principalType: String = "unknown",
    val secondaryType: String = "unknown",
    val sprites: Any = ""
) : Parcelable, Comparable<Pokemon> {
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

    override fun compareTo(other: Pokemon) = this.id - other.id

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Pokemon -> this.id == other.id
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "${this.id} - ${this.name}"
    }
}