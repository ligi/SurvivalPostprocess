package kontinuum

import java.io.File

fun main(args: Array<String>) {

    val all_images_in_text = mutableListOf<String>()
    val image_regex = Regex("!\\[([^]]*)]\\(([^)]+)\\)")

    val path = File("md")
    path.listFiles { _, name -> name.endsWith(".md") }.forEach {

        all_images_in_text.addAll(image_regex.findAll(it.readText())
                .mapNotNull { it.groups[2]?.value })

    }

    println("all_images_in text count: " + all_images_in_text.size)

    println("all_images_in text.distinct count: " + all_images_in_text.distinct().size)

    val all_images= path.list { _, name -> name.endsWith(".jpg") || name.endsWith(".png") }.toMutableList()
    println("all_images count: " + all_images.size)

    val unused_images = all_images.minus(all_images_in_text)
    println("unused_images" + unused_images)

    if (unused_images.isNotEmpty()) {
        val unusedPath = File(path, "unused").apply {
            mkdir()
        }
        unused_images.forEach {
            File(path,it).apply {
                copyTo(File(unusedPath, it),true)
                delete()
            }
        }
    }
}

