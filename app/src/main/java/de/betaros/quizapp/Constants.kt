package de.betaros.quizapp

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val question1 = Question(1, "What country is this?", R.drawable.de, "Germany", "Austria", "Italy", "France", 1)
        val question2 = Question(2, "What country is this?", R.drawable.ar, "Germany", "Armenia", "Italy", "France", 2)
        val question3 = Question(3, "What country is this?", R.drawable.at, "Germany", "Austria", "Italy", "France", 2)
        val question4 = Question(4, "What country is this?", R.drawable.hr, "Germany", "Austria", "Italy", "Croatia", 4)

        questionsList.add(question1)
        questionsList.add(question2)
        questionsList.add(question3)
        questionsList.add(question4)

        return questionsList
    }
}