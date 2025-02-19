//is connetected to OnboardingAdapter,OnboardingItem, and ItemOnboarding

package com.example.quizora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class Phase1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase1)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        // List of onboarding pages with custom text
        val onboardingItems = listOf(
            OnboardingItem(R.drawable.onboard_1, "Learn & Improve", "Enhance your knowledge with engaging quizzes on various topics."),
            OnboardingItem(R.drawable.onboard_2, "Challenge Yourself", "Test your skills with different difficulty levels and track your progress."),
            OnboardingItem(R.drawable.onboard_3, "Time-Based Quizzes", "Race against time in exciting quiz battles and boost your speed."),
            OnboardingItem(R.drawable.onboard_4, "Make Your Own Quiz", "Create custom quizzes and share them with friends or the community."),
            OnboardingItem(R.drawable.onboard_5, "View Quiz History", "Revisit past quizzes, analyze your performance, and improve your scores.")
        )

        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter
    }
}
