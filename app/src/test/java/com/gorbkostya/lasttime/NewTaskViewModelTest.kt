package com.gorbkostya.lasttime

import com.gorbkostya.lasttime.presentation.edit.NewTaskCommand
import com.gorbkostya.lasttime.presentation.edit.NewTaskViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class NewTaskViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `пустое название — сохранять нельзя`() {
        val vm = NewTaskViewModel(FakeTaskRepository())
        assertFalse(vm.state.value.canSave)
    }

    @Test
    fun `из одних пробелов — сохранять нельзя`() {
        val vm = NewTaskViewModel(FakeTaskRepository())
        vm.onCommand(NewTaskCommand.TitleChanged("  "))
        assertFalse(vm.state.value.canSave)
    }

    @Test
    fun `сохранение добавляет дело и ставит isSaved`() {
        val repo = FakeTaskRepository()
        val vm = NewTaskViewModel(repo)
        vm.onCommand(NewTaskCommand.TitleChanged("Полить кактус"))
        vm.onCommand(NewTaskCommand.Save)

        assertTrue(vm.state.value.isSaved)
    }
}