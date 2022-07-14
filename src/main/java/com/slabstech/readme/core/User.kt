package com.slabstech.readme.core

data class User(val username: String, val profileURL:String,
                val location: String, val socialLinks: Array<String>, val gravatarURL: String,
                val achievements: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (username != other.username) return false
        if (profileURL != other.profileURL) return false
        if (location != other.location) return false
        if (!socialLinks.contentEquals(other.socialLinks)) return false
        if (gravatarURL != other.gravatarURL) return false
        if (!achievements.contentEquals(other.achievements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + profileURL.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + socialLinks.contentHashCode()
        result = 31 * result + gravatarURL.hashCode()
        result = 31 * result + achievements.contentHashCode()
        return result
    }
}
