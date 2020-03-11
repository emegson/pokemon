package com.github.emerson.pokemon.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.internal.LinkedTreeMap

data class Pokemon(
    val id: Int = -1,
    val name: String = "noname",
    val types: Any,
    val sprites: Any = "",
    val height: Int,
    val weight: Int
) : Parcelable, Comparable<Pokemon> {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "noname",
        parcel.readString() ?: "unknown",
        parcel.readString() ?: "unknown",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(height)
        parcel.writeInt(weight)
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
        return when (other) {
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

    fun getImage(): String {
        return (this.sprites as LinkedTreeMap<String, String>)["front_default"] ?: "unknown"
    }

    fun getPrincipalType(): String =
        try {
            (((types as ArrayList<Any>)[0] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, String?>)["name"].toString()
        } catch (e: IndexOutOfBoundsException) {
            "unknown"
        }

    fun getSecondaryType(): String = try {
        (((types as ArrayList<Any>)[1] as LinkedTreeMap<String, Any>)["type"] as LinkedTreeMap<String, Any>)["name"].toString()
    } catch (e: IndexOutOfBoundsException) {
        "unknown"
    }
}