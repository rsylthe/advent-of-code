package com.rsylthe.aoc.common.input

import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class PuzzleInput(val lines: List<String> = listOf()) {

  companion object {
    private fun downloadInput(year: Int, day: Int, inputFile: File) {
      val fileContent = PuzzleInputDownloader.downloadInput(year, day)
      inputFile.createNewFile()
      inputFile.writeText(fileContent)
    }

    fun forDay(year: Int, day: Int, suffix: String = ""): PuzzleInput {
      val projectRoot = Path("").absolutePathString()
      val module = "/aoc-$year"
      val inputs = "/src/main/resources/inputs"
      val absolute = Path(projectRoot, module, inputs).toFile()
      absolute.mkdirs()

      val inputFile = File(absolute, "$year/$day$suffix")
      if (!inputFile.exists()) {
        println("Creating ${inputFile.absolutePath}")
        downloadInput(year, day, inputFile)
      }

      return PuzzleInput(inputFile.readLines().filter { it.isNotBlank() })
    }
  }
}
