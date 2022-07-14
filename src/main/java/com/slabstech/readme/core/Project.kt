package com.slabstech.readme.core


data class Project(val name: String, val description: String, val status:Boolean, val badges: Array<String>, val techStack: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Project

        if (name != other.name) return false
        if (description != other.description) return false
        if (status != other.status) return false
        if (!badges.contentEquals(other.badges)) return false
        if (!techStack.contentEquals(other.techStack)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + badges.contentHashCode()
        result = 31 * result + techStack.contentHashCode()
        return result
    }
}