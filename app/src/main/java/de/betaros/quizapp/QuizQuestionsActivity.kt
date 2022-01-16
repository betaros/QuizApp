package de.betaros.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question> ?= null
    private var mSelectedQuestionPosition: Int = 0
    private var mUserName: String ?= null
    private var mCorrectAnswers: Int = 0

    private var progressBar: ProgressBar ?= null
    private var tvProgress: TextView ?= null
    private var tvQuestion: TextView ?= null
    private var ivImage: ImageView ?= null

    private var tvOption1: TextView ?= null
    private var tvOption2: TextView ?= null
    private var tvOption3: TextView ?= null
    private var tvOption4: TextView ?= null
    private var btnSubmit: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        progressBar = findViewById(R.id.progressBar)
        progressBar?.max = mQuestionsList!!.size

        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)

        tvOption1 = findViewById(R.id.tv_option_1)
        tvOption2 = findViewById(R.id.tv_option_2)
        tvOption3 = findViewById(R.id.tv_option_3)
        tvOption4 = findViewById(R.id.tv_option_4)
        btnSubmit = findViewById(R.id.btn_submit)

        tvOption1?.setOnClickListener(this)
        tvOption2?.setOnClickListener(this)
        tvOption3?.setOnClickListener(this)
        tvOption4?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionsView()

        val question: Question = mQuestionsList!![mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${mQuestionsList!!.size}"
        tvQuestion?.text = question.question
        tvOption1?.text = question.option1
        tvOption2?.text = question.option2
        tvOption3?.text = question.option3
        tvOption4?.text = question.option4

        if(mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit?.text = "Finish"
        } else {
            btnSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOption1?.let {
            options.add(0, it)
        }

        tvOption2?.let {
            options.add(1, it)
        }

        tvOption3?.let {
            options.add(2, it)
        }

        tvOption4?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#000000"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.option_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()

        mSelectedQuestionPosition = selectedOptionNum
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.option_bg_selected
        )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_1 -> {
                tvOption1?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_2 -> {
                tvOption2?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_3 -> {
                tvOption3?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_4 -> {
                tvOption4?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                if(mSelectedQuestionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectedQuestionPosition) {
                        answerView(mSelectedQuestionPosition, R.drawable.option_bg_wrong)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.option_bg_correct)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btnSubmit?.text = "Finish"
                    } else {
                        btnSubmit?.text = "Go to next question..."
                    }

                    mSelectedQuestionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when(answer){
            1 -> {
                tvOption1?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvOption2?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvOption3?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvOption4?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}