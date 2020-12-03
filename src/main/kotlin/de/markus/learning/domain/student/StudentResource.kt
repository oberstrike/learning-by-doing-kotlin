package de.markus.learning.domain.student



class StudentResource {

    fun getStudent(): IStudent {
        return Student()
    }
}
