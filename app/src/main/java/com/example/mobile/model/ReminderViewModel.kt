package com.example.mobile.model

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.Graph
import com.example.mobile.repository.ReminderRepository
import kotlinx.coroutines.flow.*



class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel(){
    private val _state = MutableStateFlow(ReminderViewState())
    private val _selectedCategory = MutableStateFlow<Category?>(null)

    val state: StateFlow<ReminderViewState>
        get()=_state

    fun onCategorySelected(category: Category){
        _selectedCategory.value = category
    }

    init {
        viewModelScope.launch {

            combine(
                reminderRepository.categories().onEach { list ->
                    if (list.isNotEmpty() && _selectedCategory.value == null){
                        _selectedCategory.value = list[0]
                    }
                },
                _selectedCategory
            ) {categories, selectedCategory ->
                ReminderViewState(
                    categories = categories,
                    selectedCategory = selectedCategory
                )
            } .collect{ _state.value = it }
        }
        loadCategoriesFromdb()
    }


    private fun loadCategoriesFromdb(){
        val list = mutableListOf(
        Category(name = "Reminders")
        )
        viewModelScope.launch{
            list.forEach{ category -> reminderRepository.addCategory(category) }
        }
    }
}

data class ReminderViewState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null
)