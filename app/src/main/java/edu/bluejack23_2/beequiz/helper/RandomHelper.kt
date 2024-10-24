package edu.bluejack23_2.beequiz.helper

object RandomHelper {

    fun generateNumber(min: Int, max: Int): Int {
        return (min..max).random()
    }

    fun generateColor(): String {
        val colors = listOf(
            "#FFB3BA", // Light Pink
            "#FFDFBA", // Peach
            "#FFFFBA", // Light Yellow
            "#BAFFC9", // Light Green
            "#BAE1FF", // Light Blue
            "#FFB3D9", // Light Magenta
            "#C9BAFF", // Light Purple
            "#FFE4BA", // Light Apricot
            "#BAFFD9", // Mint
            "#FFBABA", // Light Coral
            "#FFC3A0", // Light Salmon
            "#FFDAC1", // Light Blush
            "#E2F0CB", // Light Lime
            "#FFABAB", // Light Red
            "#D4A5A5", // Light Rose
            "#FFCCF9", // Light Lavender
            "#FFC8E2", // Light Fuchsia
            "#C4F0C5"  // Light Emerald
        )
        return colors.random()
    }
}