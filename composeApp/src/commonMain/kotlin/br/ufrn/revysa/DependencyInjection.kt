package br.ufrn.revysa

import br.ufrn.revysa.data.demo.InMemoryStudyRepository
import br.ufrn.revysa.domain.model.StudySnapshot
import br.ufrn.revysa.domain.repository.StudyRepository
import br.ufrn.revysa.domain.usecase.EpochClock
import br.ufrn.revysa.domain.usecase.StudyUseCases
import br.ufrn.revysa.presentation.StudyViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun appModule(
    initialSnapshot: StudySnapshot,
    onSnapshotChanged: (StudySnapshot) -> Unit,
): Module =
    module {
        single<StudyRepository> { InMemoryStudyRepository(initialSnapshot, onSnapshotChanged) }
        single { EpochClock { platformEpochMillis() } }
        single { StudyUseCases(get(), get()) }
        viewModel { StudyViewModel(get()) }
    }
