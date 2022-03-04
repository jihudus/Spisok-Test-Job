package ru.kurant.spisoktest.domain

import kotlinx.serialization.Serializable

@Serializable
data class Spisok(
    val contacts: List<Contact>
)
