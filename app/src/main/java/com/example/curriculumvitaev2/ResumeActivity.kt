package com.example.curriculumvitaev2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView


class ResumeActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var profileImage: ImageView
    private lateinit var skillsBtn: Button
    private lateinit var hobbiesBtn: Button
    private lateinit var langBtn: Button
    private lateinit var toolbar: Toolbar
    private lateinit var fragContainer: FragmentContainerView
    private lateinit var basicInfoFragment: BasicInfoFragment
    private var cvObject: CvObject? = null
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.info -> {
            navigateToFragment(R.id.fragContainer, supportFragmentManager, basicInfoFragment, "nav")
            true
        }

        R.id.logout -> {

                alert(this, title = R.string.logout, body = R.string.logout_prompt, positiveAction = {
                    sharedPrefs.edit().clear().apply()
                    finish()
                })

                true
        }



        else -> super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        toolbar = findViewById(R.id.toolBar)

        sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)



        println("CV: $cvObject")


        name = findViewById(R.id.nameText)
        email = findViewById(R.id.emailText)
        profileImage = findViewById(R.id.profilePic)
        fragContainer = findViewById(R.id.fragContainer)

        skillsBtn = findViewById(R.id.skillsBtn)
        hobbiesBtn = findViewById(R.id.hobbiesBtn)
        langBtn = findViewById(R.id.langBtn)


        setSupportActionBar(toolbar)

        name.text = cvObject!!.fullName
        email.text = cvObject!!.email

        when {
            sharedPrefs.getBoolean(IS_GRANTED_READ_IMAGES, false) -> profileImage.setImageURI(
                cvObject!!.imgURI
            )
            else -> profileImage.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_image))
        }


        basicInfoFragment = BasicInfoFragment.new(
            cvObject!!.fullName!!, cvObject!!.gender!!, cvObject!!.age!!, cvObject!!.email!!
        )

        val skillsFragment = SkillsFragment.new(
            androidP = cvObject!!.skillsScore!![ANDROID_KEY]!!,
            flutterP = cvObject!!.skillsScore!![FLUTTER_KEY]!!,
            iosP = cvObject!!.skillsScore!![iOS_KEY]!!,
        )

        val hobbiesFragment = HobbiesFragment.new(
            isMusic = cvObject!!.hobbies!![MUSIC]!!,
            isGames = cvObject!!.hobbies!![GAMES]!!,
            isSport = cvObject!!.hobbies!![SPORT]!!
        )

        val languageFragment = LanguageFragment.new(
            isArabic = cvObject!!.languages!![AR]!!,
            isEnglish = cvObject!!.languages!![EN]!!,
            isFrench = cvObject!!.languages!![FR]!!
        )

        supportFragmentManager.beginTransaction().add(
            R.id.fragContainer, skillsFragment
        ).commit()

        skillsBtn.setOnClickListener {
            navigateToFragment(R.id.fragContainer, supportFragmentManager, skillsFragment, "nav")
        }

        hobbiesBtn.setOnClickListener {
            navigateToFragment(R.id.fragContainer, supportFragmentManager, hobbiesFragment, "nav")
        }

        langBtn.setOnClickListener {
            navigateToFragment(R.id.fragContainer, supportFragmentManager, languageFragment, "nav")
        }



    }
}
