package com.example.ecotrack

import android.content.Context
import android.content.SharedPreferences

class GoalPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var dailyGoal: Int
        get() = prefs.getInt(KEY_DAILY_GOAL, DEFAULT_GOAL)
        set(value) {
            prefs.edit().putInt(KEY_DAILY_GOAL, value).apply()
        }

    companion object {
        private const val PREF_NAME = "ecotrack_goal_prefs"
        private const val KEY_DAILY_GOAL = "key_daily_goal"
        const val DEFAULT_GOAL = 2000 // default target in ml
    }
}
