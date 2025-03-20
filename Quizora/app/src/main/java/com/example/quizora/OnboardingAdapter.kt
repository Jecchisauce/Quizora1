package com.example.quizora

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val onboardingImage: ImageView = view.findViewById(R.id.OnboardingImg)
        private val onboardingTitle: TextView = view.findViewById(R.id.Onboardingtitle)
        private val onboardingDescription: TextView = view.findViewById(R.id.OnboardingDescript)

        fun bind(onboardingItem: OnboardingItem) {
            onboardingImage.setImageResource(onboardingItem.imageResId)
            onboardingTitle.text = onboardingItem.title
            onboardingDescription.text = onboardingItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}
