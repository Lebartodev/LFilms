package com.lebartodev.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentArgumentDelegate<T : Any> : ReadOnlyProperty<Fragment, T> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val key = property.name
        return thisRef.arguments?.get(key) as? T
            ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}

class FragmentNullableArgumentDelegate<T : Any?> : ReadOnlyProperty<Fragment, T?> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        val key = property.name
        return thisRef.arguments?.get(key) as? T
    }
}

fun <T : Any> fragmentArgs(): ReadOnlyProperty<Fragment, T> =
    FragmentArgumentDelegate()

fun <T : Any> fragmentNullableArgs(): ReadOnlyProperty<Fragment, T?> =
    FragmentNullableArgumentDelegate()