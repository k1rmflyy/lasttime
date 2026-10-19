//package com.gorbkostya.lasttime
//
//import com.gorbkostya.lasttime.domain.model.Category
//import com.gorbkostya.lasttime.presentation.list.ListCommand
//import com.gorbkostya.lasttime.presentation.list.ListViewModel
//import com.gorbkostya.lasttime.presentation.list.previewToday
//import org.junit.Test
//
//class ListViewModelTest {
//
//    @Test
//    fun `выбор категории фильтрует список`() {
//        val vm = ListViewModel()
//        vm.onCommand(ListCommand.SelectCategory(Category.HOME))
//
//        val visible = vm.state.value.visible
//        assert(visible.all { it.category == Category.HOME })
//    }
//
//    @Test
//    fun `сделано обновляет дату`() {
//        val vm = ListViewModel()
//        vm.onCommand(ListCommand.MarkDone(1))
//
//        val taskId = vm.state.value.tasks.first { it.id == 1L }
//        assert(taskId.lastDone == previewToday)
//    }
//}