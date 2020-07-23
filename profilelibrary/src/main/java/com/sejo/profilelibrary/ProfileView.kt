package com.sejo.profilelibrary

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ProfileView(context : Context, attrs : AttributeSet) : LinearLayout(context, attrs) {

    private val profilePic : ImageView
    private val username : TextView
    private val email : TextView
    private val number : TextView

    init {
        inflate(context, R.layout.profile_view, this)

        profilePic = findViewById(R.id.profile_pic)
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        number = findViewById(R.id.number)

        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.ProfileView,0,0)
        profilePic.setImageDrawable(attributes.getDrawable(R.styleable.ProfileView_image))
        username.text = attributes.getString(R.styleable.ProfileView_username)
        email.text = attributes.getString(R.styleable.ProfileView_email)
        number.text = attributes.getString(R.styleable.ProfileView_number)
        attributes.recycle()
    }

    fun setUsername( username : String){
        this.username.text = username
        invalidate()
        requestLayout()
    }

    fun getUsername() : String{
        return this.username.text.toString()
    }

    fun setEmail( email : String){
        this.email.text = email
        invalidate()
        requestLayout()
    }

    fun getEmail() : String{
        return this.email.text.toString()
    }

    fun setNumber( number : String){
        this.number.text = number
        invalidate()
        requestLayout()
    }

    fun getNumber() : String{
        return this.number.text.toString()
    }
}