package com.example.list_view

import androidx.annotation.DrawableRes

data class Destination(
    val id: Int,
    val title: String,
    val category: String,
    val location: String,
    val rating: Double,
    val reviewCount: String,
    val description: String,
    @get:DrawableRes val imageResId: Int,
) {
    companion object {
        val SAMPLE_DESTINATIONS = listOf(
            Destination(
                id = 1,
                title = "Alpine Summit Pass",
                category = "MOUNTAIN",
                location = "Swiss Alps, Switzerland",
                rating = 4.9,
                reviewCount = "1.2k",
                description = "Breathtaking mountain peak featuring pristine panoramic views, crystal alpine lakes, and world-class hiking trails through snow-capped vistas.",
                imageResId = R.drawable.img_mountain,
            ),
            Destination(
                id = 2,
                title = "Emerald Haven Lagoon",
                category = "OCEAN",
                location = "Bora Bora, French Polynesia",
                rating = 4.8,
                reviewCount = "2.4k",
                description = "Turquoise coastal waters fringed with lush tropical palms and vibrant coral reefs ideal for diving, relaxing, and watching golden island sunsets.",
                imageResId = R.drawable.img_ocean,
            ),
            Destination(
                id = 3,
                title = "Whispering Pines Forest",
                category = "FOREST",
                location = "Black Forest, Germany",
                rating = 4.7,
                reviewCount = "980",
                description = "Mist-shrouded ancient woodland paths lined with towering evergreens, tranquil cascading streams, and enchanting fairy-tale scenery.",
                imageResId = R.drawable.img_forest,
            ),
            Destination(
                id = 4,
                title = "Saharan Golden Dunes",
                category = "DESERT",
                location = "Merzouga, Morocco",
                rating = 4.9,
                reviewCount = "1.8k",
                description = "Sweeping wind-sculpted sand dunes glowing red and orange under warm desert suns, offering camel treks and starlit oasis campsites.",
                imageResId = R.drawable.img_desert,
            ),
            Destination(
                id = 5,
                title = "Aurora Celestial Fjord",
                category = "MOUNTAIN",
                location = "Tromsø, Norway",
                rating = 5.0,
                reviewCount = "3.1k",
                description = "Magical arctic landscape where vivid green northern lights dance across starlit skies over glassy fjords and snowy peaks.",
                imageResId = R.drawable.img_aurora,
            ),
            Destination(
                id = 6,
                title = "Crimson Gorge Canyon",
                category = "DESERT",
                location = "Arizona, United States",
                rating = 4.8,
                reviewCount = "1.5k",
                description = "Majestic carved rock formations with dramatic layered sandstone cliffs, glowing in rich ochre and amber tones during sunrise and golden hour.",
                imageResId = R.drawable.img_canyon,
            ),
        )
    }
}