package com.fitscorp.sl.apps.di



import com.fitscorp.sl.apps.App
import com.fitscorp.sl.apps.Resend_Code
import com.fitscorp.sl.apps.home.MainActivity
import com.fitscorp.sl.apps.home.SplashActivity
import com.fitscorp.sl.apps.login.FrogotPassword
import com.fitscorp.sl.apps.login.LoginActivity
import com.fitscorp.sl.apps.menu.ContactUsFragment
import com.fitscorp.sl.apps.menu.LeaderboardFragment
import com.fitscorp.sl.apps.menu.TimelineFragment
import com.fitscorp.sl.apps.profile.EditProfile
import com.fitscorp.sl.apps.profile.ProfileActivity
import com.fitscorp.sl.apps.register.RegisterActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [],modules = [MainModule::class])
interface MyComponent {

    fun inject(app: App)
    fun inject(registerActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(frogotPassword: FrogotPassword)
    fun inject(contactUsFragment: ContactUsFragment)
    fun inject(leaderboardFragment: LeaderboardFragment)
    fun inject(timelineFragment: TimelineFragment)
    fun inject(profileActivity: ProfileActivity)
    fun inject(editprofileActivity: EditProfile)
    fun inject(editprofileActivity: Resend_Code)


}