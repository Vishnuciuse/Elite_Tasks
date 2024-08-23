package com.example.myapplication.task3

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMain4Binding
import java.security.AccessController.getContext

class MainActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain4Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentLayout, StepOneFragment())
        fragmentTransaction.commit()

        val count = 9
        countOfSteps(count)

        var stepPosition = 1

        binding.goNextIV.setOnClickListener {
            if (stepPosition<count) {
                when (stepPosition) {
                    1 -> {
                        binding.stepTwoIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorTwo.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepTwoFragment())
                        stepPosition = 2
                    }

                    2 -> {
                        binding.stepThreeIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorThree.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepThreeFragment())
                        stepPosition = 3
                    }

                    3 -> {
                        binding.stepFourIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorFour.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepOneFragment())
                        stepPosition = 4
                    }

                    4 -> {
                        binding.stepFiveIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorFive.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepOneFragment())
                        stepPosition = 5
                    }

                    5 -> {
                        binding.stepSixIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorSix.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepTwoFragment())
                        stepPosition = 6
                    }

                    6 -> {
                        binding.stepSevenIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorSeven.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepThreeFragment())
                        stepPosition = 7
                    }

                    7 -> {
                        binding.stepEightIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorEight.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepOneFragment())
                        stepPosition = 8
                    }

                    8 -> {
                        binding.stepNineIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorNine.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepTwoFragment())
                        stepPosition = 9
                    }

                    9 -> {
                        binding.stepTenIndicatorV.visibility = View.VISIBLE
                        binding.dotIndicatorTen.setColorFilter(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        fragmentReplace(StepThreeFragment())
                        stepPosition = 10
                    }

                    10 -> {
                        binding.stepElevenIndicatorV.visibility = View.VISIBLE
                        fragmentReplace(StepOneFragment())
                        stepPosition = 11
                    }

                }
            }
        }

        binding.goBackIV.setOnClickListener {
            when (stepPosition) {
                11 -> {
                    binding.stepElevenIndicatorV.visibility = View.GONE
                    fragmentReplace(StepOneFragment())
                    stepPosition = 10
                }
                10 -> {
                    binding.stepTenIndicatorV.visibility = View.GONE
                    binding.dotIndicatorTen.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepThreeFragment())
                    stepPosition = 9
                }
                9 -> {
                    binding.stepNineIndicatorV.visibility = View.GONE
                    binding.dotIndicatorNine.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepTwoFragment())
                    stepPosition = 8
                }
                8 -> {
                    binding.stepEightIndicatorV.visibility = View.GONE
                    binding.dotIndicatorEight.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepOneFragment())
                    stepPosition = 7
                }
                7 -> {
                    binding.stepSevenIndicatorV.visibility = View.GONE
                    binding.dotIndicatorSeven.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepThreeFragment())
                    stepPosition = 6
                }
                6 -> {
                    binding.stepSixIndicatorV.visibility = View.GONE
                    binding.dotIndicatorSix.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepTwoFragment())
                    stepPosition = 5
                }
                5 -> {
                    binding.stepFiveIndicatorV.visibility = View.GONE
                    binding.dotIndicatorFive.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepOneFragment())
                    stepPosition = 4
                }
                4 -> {
                    binding.stepFourIndicatorV.visibility = View.GONE
                    binding.dotIndicatorFour.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepThreeFragment())
                    stepPosition = 3
                }
                3 -> {
                    binding.stepThreeIndicatorV.visibility = View.GONE
                    binding.dotIndicatorThree.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepTwoFragment())
                    stepPosition = 2
                }
                2 -> {
                    binding.stepTwoIndicatorV.visibility = View.GONE
                    binding.dotIndicatorTwo.setColorFilter(ContextCompat.getColor(this, R.color.dotNotPresent))
                    fragmentReplace(StepOneFragment())
                    stepPosition = 1
                }
            }
        }

    }

    fun Int.toDp(context: Context): Float {
        return this / context.resources.displayMetrics.density
    }

    fun Context.dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    private fun countOfSteps(count: Int) {
        val maxWidth = 220f
        val newWidth = dpToPx(maxWidth) / count
        binding.stepOneIndicatorV.layoutParams.width = newWidth
        binding.stepTwoIndicatorV.layoutParams.width = newWidth
        binding.stepThreeIndicatorV.layoutParams.width = newWidth
        binding.stepFourIndicatorV.layoutParams.width = newWidth
        binding.stepFiveIndicatorV.layoutParams.width = newWidth
        binding.stepSixIndicatorV.layoutParams.width = newWidth
        binding.stepSevenIndicatorV.layoutParams.width = newWidth
        binding.stepEightIndicatorV.layoutParams.width = newWidth
        binding.stepNineIndicatorV.layoutParams.width = newWidth
        binding.stepTenIndicatorV.layoutParams.width = newWidth
        binding.stepElevenIndicatorV.layoutParams.width = newWidth

        binding.constraintLayout2.layoutParams.width = newWidth * count + dpToPx(25f)

        val views: List<View> = listOf(
            findViewById(R.id.stepOneIndicatorV),
            findViewById(R.id.stepTwoIndicatorV),
            findViewById(R.id.stepThreeIndicatorV),
            findViewById(R.id.stepFourIndicatorV),
            findViewById(R.id.stepFiveIndicatorV),
            findViewById(R.id.stepSixIndicatorV),
            findViewById(R.id.stepSevenIndicatorV),
            findViewById(R.id.stepEightIndicatorV),
            findViewById(R.id.stepNineIndicatorV),
            findViewById(R.id.stepTenIndicatorV),
            findViewById(R.id.stepElevenIndicatorV)
        )

        val dotImages: List<ImageView> = listOf(
            findViewById(R.id.dotIndicatorOne),
            findViewById(R.id.dotIndicatorTwo),
            findViewById(R.id.dotIndicatorThree),
            findViewById(R.id.dotIndicatorFour),
            findViewById(R.id.dotIndicatorFive),
            findViewById(R.id.dotIndicatorSix),
            findViewById(R.id.dotIndicatorSeven),
            findViewById(R.id.dotIndicatorEight),
            findViewById(R.id.dotIndicatorNine),
            findViewById(R.id.dotIndicatorTen)
        )

//        for (i in views.indices) {
//            views[i].visibility = if (i < count) View.VISIBLE else View.GONE
//        }

        for (j in dotImages.indices){
            dotImages[j].visibility = if (j < count-1) View.VISIBLE else View.GONE
        }


    }

    private fun fragmentReplace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentLayout, fragment)
        fragmentTransaction.commit()
    }


}