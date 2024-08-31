package com.example.baker_street.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baker_street.models.AnnouncementsModel
import com.example.baker_street.models.AssignmentsModel
import com.example.baker_street.models.MaterialModel
import com.example.baker_street.models.MaterialsModel
import com.example.baker_street.repositories.ClassroomRepo

class ClassroomViewModel : ViewModel() {
    private var message: MutableLiveData<String>? = null
    private var announcements: MutableLiveData<AnnouncementsModel>? = null
    private var materials: MutableLiveData<MaterialsModel>? = null
    private var assignments: MutableLiveData<AssignmentsModel>? = null
    private var repo: ClassroomRepo? = null

    init {
        repo = ClassroomRepo().getInstance()
        message = MutableLiveData<String>()
        announcements = MutableLiveData<AnnouncementsModel>()
        materials = MutableLiveData<MaterialsModel>()
        assignments = MutableLiveData<AssignmentsModel>()
    }

    fun getAnnouncements(jwtToken: String, courseid: String) {
        repo?.getAnnouncements(jwtToken, courseid)
    }
    fun getClassMaterials(jwtToken: String, courseid: String) {
        repo?.getClassMaterials(jwtToken, courseid)
    }

    fun getAssignments(jwtToken: String, courseid: String) {
        repo?.getAssignments(jwtToken, courseid)
    }

    fun getMessageObserver(): MutableLiveData<String>? {
        message = repo?.getMessageObserver()
        return message
    }

    fun getAnnouncementsObserver(): MutableLiveData<AnnouncementsModel>? {
        announcements = repo?.getAnnouncementsObserver()
        return announcements
    }
    fun getClassMaterialsObserver(): MutableLiveData<MaterialsModel>? {
        materials = repo?.getClassMaterialssObserver()
        return materials
    }
    fun getAssignmentsObserver(): MutableLiveData<AssignmentsModel>? {
        assignments = repo?.getAssignmentsObserver()
        return assignments
    }

}