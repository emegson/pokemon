package com.github.emerson.pokemon.ui.main.adapter

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.google.android.material.card.MaterialCardView

class ButtonMaterialCardView(context: Context?, attrs: AttributeSet? = null) :
    MaterialCardView(context,attrs){

    override fun getAccessibilityClassName(): CharSequence {
        return Button::class.java.name
    }
}