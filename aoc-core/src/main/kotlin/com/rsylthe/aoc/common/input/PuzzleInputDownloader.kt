package com.rsylthe.aoc.common.input

import com.rsylthe.aoc.exceptions.CookieExpired
import com.rsylthe.aoc.exceptions.CookieMissing
import com.rsylthe.aoc.exceptions.MerryChristmasPunk
import okhttp3.OkHttpClient
import okhttp3.Request

object PuzzleInputDownloader {
  private const val ERROR_LOGIN = "Puzzle inputs differ by user.  Please log in to get your puzzle input."
  private val sessionCookieFile = this::class.java.getResource("/session.cookie") ?: throw CookieMissing()
  private val client = OkHttpClient()

  fun downloadInput(year: Int, day: Int): String {
    val url = "https://adventofcode.com/$year/day/$day/input"
    val sessionCookie = sessionCookieFile.readText()
    val request = Request.Builder().url(url).addHeader("Cookie", "session=$sessionCookie").build()

    client.newCall(request).execute().use { response ->
      val body = response.body ?: throw MerryChristmasPunk()

      return body.string().let { bodyText ->
        if (bodyText.trim() == ERROR_LOGIN) throw CookieExpired() else bodyText
      }
    }
  }
}
