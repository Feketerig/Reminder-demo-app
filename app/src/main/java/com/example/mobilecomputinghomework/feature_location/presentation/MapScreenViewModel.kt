package com.example.mobilecomputinghomework.feature_location.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghomework.feature_reminder.domain.ReminderRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _reminders = MutableStateFlow(emptyList<ClusterMarker>())
    val reminders = _reminders.asStateFlow()

    init {
        viewModelScope.launch {
            _reminders.value = reminderRepository.getRemindersWithLocation().map {
                ClusterMarker(
                    itemPosition = LatLng(it.lat!!, it.lon!!),
                    itemTitle = it.message,
                    itemSnippet = "",
                    radius = it.radius!!
                )
            }
        }
    }
}



data class ClusterMarker(
    val itemPosition: LatLng,
    val itemTitle: String,
    val itemSnippet: String,
    val radius: Int
) : ClusterItem {
    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet
}