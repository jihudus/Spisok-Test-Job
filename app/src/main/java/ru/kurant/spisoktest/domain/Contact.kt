package ru.kurant.spisoktest.domain

import android.icu.text.SimpleDateFormat
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["id"])
data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val height: Float,
    val biography: String,
    val temperament: Temperament,
    @Embedded(prefix = "period_")
    val educationPeriod: EducationPeriod
) {
    @Serializable
    data class EducationPeriod(
        val start: String,
        val end: String
    ) {
        override fun toString(): String {
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val sdf2 = SimpleDateFormat("dd.MM.yyyy")
            return sdf2.format(sdf1.parse(start.take(10))) + " - " + sdf2.format(sdf1.parse(end.take(10)))
        }
    }
}

enum class Temperament {
    melancholic, phlegmatic, sanguine, choleric
}
