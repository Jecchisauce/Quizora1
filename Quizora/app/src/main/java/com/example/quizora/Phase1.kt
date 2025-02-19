package com.example.quizora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class Phase1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase1)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val btnSkip: Button = findViewById(R.id.btn_skip) // Find the Skip button

        // List of onboarding pages with custom text
        val onboardingItems = listOf(
            OnboardingItem(R.drawable.onboard_1, "Learn & Improve", "Enhance your knowledge with engaging quizzes on various topics."),
            OnboardingItem(R.drawable.onboard_2, "Challenge Yourself", "Test your skills with different difficulty levels and track your progress."),
            OnboardingItem(R.drawable.onboard_3, "Time-Based Quizzes", "Compete in thrilling quiz battles and enhance your knowledge."),
            OnboardingItem(R.drawable.onboard_4, "Make Your Own Quiz", "Create custom quizzes and share them with friends or the community."),
            OnboardingItem(R.drawable.onboard_5, "View Quiz History", "Revisit past quizzes, analyze your performance, and improve your scores.")
        )

        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter

        // Skip button click event
        btnSkip.setOnClickListener {
            val intent = Intent(this, LoginForm::class.java) // Change MainActivity to the target screen
            startActivity(intent)
            finish() // Close onboarding screen
        }
    }
}
