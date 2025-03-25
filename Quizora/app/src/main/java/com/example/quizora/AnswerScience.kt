//package com.example.quizora
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.os.Bundle
//import android.os.CountDownTimer
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ProgressBar
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.addCallback
//import androidx.fragment.app.Fragment
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class AnswerScience : Fragment() {
//
//    private lateinit var timerProgressBar: ProgressBar
//    private lateinit var questionText: TextView
//    private lateinit var scoreboard: TextView
//    private lateinit var answerA: Button
//    private lateinit var answerB: Button
//    private lateinit var answerC: Button
//    private lateinit var answerD: Button
//
//    private var remainingTime: Long = 20000 // Default 20 seconds
//    private var canGoBack = false // Prevents showing progress dialog before the next question
//    private var score = 0
//    private var currentQuestionIndex = 0
//    private var isAnswered = false
//    private var timer: CountDownTimer? = null
//
//    private val questions = listOf(
//        Question("What is the chemical symbol for water?", listOf("O₂", "H₂O", "CO₂", "NaCl"), 1),
//        Question("Which planet is known as the Red Planet?", listOf("Venus", "Mars", "Jupiter", "Saturn"), 1),
//        Question("What gas do plants absorb from the atmosphere?", listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"), 1),
//        Question("What is the hardest natural substance on Earth?", listOf("Gold", "Iron", "Diamond", "Platinum"), 2),
//        Question("Which organ is responsible for pumping blood in the human body?", listOf("Brain", "Liver", "Heart", "Lungs"), 2),
//        Question("What is the process by which plants make their food?", listOf("Respiration", "Photosynthesis", "Digestion", "Evaporation"), 1),
//        Question("Which part of the cell contains genetic material?", listOf("Nucleus", "Ribosome", "Cytoplasm", "Mitochondria"), 0),
//        Question("What force keeps us on the ground?", listOf("Magnetism", "Gravity", "Friction", "Electricity"), 1),
//        Question("What is the boiling point of water in Celsius?", listOf("50°C", "100°C", "150°C", "200°C"), 1),
//        Question("What type of energy is produced by the Sun?", listOf("Nuclear", "Thermal", "Solar", "Kinetic"), 2),
//    )
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_answer_science, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.GONE
//
//        timerProgressBar = view.findViewById(R.id.timer_progress)
//        scoreboard = view.findViewById(R.id.scoreboard)
//        questionText = view.findViewById(R.id.question_text)
//        answerA = view.findViewById(R.id.answer_a)
//        answerB = view.findViewById(R.id.answer_b)
//        answerC = view.findViewById(R.id.answer_c)
//        answerD = view.findViewById(R.id.answer_d)
//
//        val backButton = view.findViewById<Button>(R.id.Science_backbtn)
//        backButton.setOnClickListener {
//            stopQuizTimer()
//            showQuizProgressDialog()
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            if (!isAnswered || !canGoBack) return@addCallback // Block back press if answer isn't declared or next question isn't ready
//            stopQuizTimer()
//            showQuizProgressDialog()
//        }
//
//        loadQuestion()
//    }
//
//    private fun stopQuizTimer() {
//        timer?.cancel() // Cancel the timer if it's running
//    }
//
//    private fun showQuizProgressDialog() {
//        val dialog = QuizProgressDialog(
//            onReturn = { parentFragmentManager.popBackStack() },
//            onRetry = { restartQuiz() },
//            onContinue = { resumeQuiz() } // Call resume function when "Continue" is clicked
//        )
//        dialog.show(parentFragmentManager, "QuizProgressDialog")
//    }
//
//    private var quizPaused = false // Track if the quiz was paused
//
//    private fun resumeQuiz() {
//        println("Resuming quiz with remaining time: $remainingTime")
//        startTimer(remainingTime)
//        quizPaused = false
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun restartQuiz() {
//        score = 0
//        currentQuestionIndex = 0
//        scoreboard.text = "SCORE: $score" // Reset UI score
//
//        val questionCounter = view?.findViewById<TextView>(R.id.question_counter)
//        questionCounter?.text = "Question 1/${questions.size}"
//
//        loadQuestion()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        requireActivity().findViewById<View>(R.id.bottom_nav1)?.visibility = View.VISIBLE
//        timer?.cancel()
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun loadQuestion() {
//        if (currentQuestionIndex >= questions.size) {
//            endQuiz()
//            return
//        }
//
//        val questionCounter = view?.findViewById<TextView>(R.id.question_counter)
//        questionCounter?.text = "Question ${currentQuestionIndex + 1}/${questions.size}"
//
//        isAnswered = false
//        val currentQuestion = questions[currentQuestionIndex]
//        questionText.text = currentQuestion.question
//        answerA.text = "A: ${currentQuestion.choices[0]}"
//        answerB.text = "B: ${currentQuestion.choices[1]}"
//        answerC.text = "C: ${currentQuestion.choices[2]}"
//        answerD.text = "D: ${currentQuestion.choices[3]}"
//
//        resetButtons()
//        startTimer(20000)
//
//        answerA.setOnClickListener { checkAnswer(0, answerA) }
//        answerB.setOnClickListener { checkAnswer(1, answerB) }
//        answerC.setOnClickListener { checkAnswer(2, answerC) }
//        answerD.setOnClickListener { checkAnswer(3, answerD) }
//    }
//
//    private fun endQuiz() {
//        saveQuizResult()
//
//        val scoreboardFragment = ScoreboardFragment.newInstance(score)
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, scoreboardFragment)
//            .addToBackStack(null)
//            .commit()
//    }
//
//    private fun saveQuizResult() {
//        val sharedPref = requireActivity().getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE)
//        val gson = Gson()
//
//        val json = sharedPref.getString("QUIZ_HISTORY", "[]")
//        val type = object : TypeToken<List<QuizHistory>>() {}.type
//        val historyList: MutableList<QuizHistory> = gson.fromJson(json, type) ?: mutableListOf()
//
//        // Calculate accuracy
//        val accuracy = if (questions.isNotEmpty()) (score.toDouble() / questions.size * 100).toInt() else 0
//        val quizEntry = QuizHistory("Science Quiz", "Accuracy: $accuracy%", R.drawable.scie_nce)
//
//        // ✅ Check if a Science Quiz entry already exists
//        val existingIndex = historyList.indexOfFirst { it.title == "Science Quiz" }
//
//        if (existingIndex != -1) {
//            // 🔄 Replace the existing entry
//            historyList[existingIndex] = quizEntry
//        } else {
//            // ➕ Add new entry if not found
//            historyList.add(quizEntry)
//        }
//
//        // Save updated history back to SharedPreferences
//        sharedPref.edit().putString("QUIZ_HISTORY", gson.toJson(historyList)).apply()
//
//        Log.d("QuizSave", "Quiz result saved (updated): $quizEntry")
//    }
//
//    private fun startTimer(time: Long) {
//        timer?.cancel() // Cancel any previous timer
//        timerProgressBar.progress = (time / 200).toInt()
//        remainingTime = time // Store the remaining time
//
//        timer = object : CountDownTimer(time, 200) {
//            override fun onTick(millisUntilFinished: Long) {
//                remainingTime = millisUntilFinished // Continuously update remaining time
//                timerProgressBar.progress = (millisUntilFinished / 200).toInt()
//            }
//
//            override fun onFinish() {
//                timerProgressBar.progress = 0
//                if (!isAnswered) {
//                    Toast.makeText(requireContext(), "Time is up!", Toast.LENGTH_SHORT).show()
//                }
//                nextQuestion()
//            }
//        }.start()
//    }
//
//    private fun checkAnswer(selectedIndex: Int, selectedButton: Button) {
//        if (isAnswered) return
//
//        isAnswered = true
//        timer?.cancel()
//
//        val correctIndex = questions[currentQuestionIndex].correctAnswer
//        if (selectedIndex == correctIndex) {
//            selectedButton.setBackgroundResource(R.drawable.correct_answer)
//            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
//            updateScore()
//        } else {
//            selectedButton.setBackgroundResource(R.drawable.wrong_answer)
//            Toast.makeText(requireContext(), "Wrong answer!", Toast.LENGTH_SHORT).show()
//        }
//
//        disableAllButtons()
//        nextQuestionDelayed()
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateScore() {
//        score += 1
//        scoreboard.text = "SCORE: $score"
//    }
//
//    private fun disableAllButtons() {
//        answerA.isEnabled = false
//        answerB.isEnabled = false
//        answerC.isEnabled = false
//        answerD.isEnabled = false
//    }
//
//    private fun resetButtons() {
//        answerA.setBackgroundResource(R.drawable.b)
//        answerB.setBackgroundResource(R.drawable.b)
//        answerC.setBackgroundResource(R.drawable.b)
//        answerD.setBackgroundResource(R.drawable.b)
//
//        answerA.isEnabled = true
//        answerB.isEnabled = true
//        answerC.isEnabled = true
//        answerD.isEnabled = true
//    }
//
//    private fun nextQuestionDelayed() {
//        val backButton = view?.findViewById<Button>(R.id.Science_backbtn)
//        backButton?.isEnabled = false // Disable back button
//
//        view?.postDelayed({
//            currentQuestionIndex++
//            loadQuestion()
//            backButton?.isEnabled = true // Enable back button after delay
//        }, 2500)
//    }
//
//    private fun nextQuestion() {
//        currentQuestionIndex++
//        loadQuestion()
//    }
//}
//
//data class Question(
//    val question: String,
//    val choices: List<String>,
//    val correctAnswer: Int
//)
