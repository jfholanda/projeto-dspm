package br.ufrn.revysa.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.ufrn.revysa.domain.model.ReviewRating
import br.ufrn.revysa.domain.model.StudyHomeSummary
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.usecase.StudyUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    data class Empty<T>(val value: T) : UiState<T>

    data class Success<T>(val value: T) : UiState<T>
}

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("TooManyFunctions")
internal class StudyViewModel(private val useCases: StudyUseCases) : ViewModel() {
    private val retryRequests = MutableStateFlow(0)

    val state =
        retryRequests
            .flatMapLatest {
                useCases.observeStudy()
                    .map<StudySnapshot, UiState<StudySnapshot>> { snapshot ->
                        if (snapshot.savedMaterialIds.isEmpty() && snapshot.cards.isEmpty()) {
                            UiState.Empty(snapshot)
                        } else {
                            UiState.Success(snapshot)
                        }
                    }
                    .onStart { emit(UiState.Loading) }
                    .catch { emit(UiState.Error(it.message ?: "Não foi possível carregar os dados.")) }
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Loading)

    fun retry() = retryRequests.update { it + 1 }

    fun materials() = useCases.allMaterials()

    fun libraryMaterials(
        snapshot: StudySnapshot,
        query: String,
        explore: Boolean,
    ) = useCases.libraryMaterials(snapshot, query, explore)

    fun progress(
        snapshot: StudySnapshot,
        material: br.ufrn.revysa.domain.model.StudyMaterial,
    ) = useCases.progress(snapshot, material)

    fun material(id: String) = useCases.material(id)

    fun addMaterial(id: String) = useCases.addMaterial(id)

    fun markChapterRead(
        materialId: String,
        chapter: Int,
    ) = useCases.markChapterRead(materialId, chapter)

    fun toggleHighlight(
        materialId: String,
        chapter: Int,
    ) = useCases.toggleHighlight(materialId, chapter)

    fun saveNote(
        materialId: String,
        chapter: Int,
        text: String,
    ) = useCases.saveNote(materialId, chapter, text)

    fun createCard(
        materialId: String,
        chapter: Int,
        question: String,
        answer: String,
    ) = useCases.createCard(materialId, chapter, question, answer)

    fun submitQuiz(
        materialId: String,
        selectedAnswer: Int,
    ) = useCases.submitQuiz(materialId, selectedAnswer)

    fun dueCards() = useCases.reviewDueCards()

    fun homeSummary(snapshot: StudySnapshot): StudyHomeSummary = useCases.homeSummary(snapshot)

    fun recordReview(
        cardId: String,
        rating: ReviewRating,
    ) = useCases.recordReview(cardId, rating)
}
