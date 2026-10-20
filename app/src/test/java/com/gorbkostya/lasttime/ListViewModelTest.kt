import com.gorbkostya.lasttime.FakeTaskRepository
import com.gorbkostya.lasttime.MainDispatcherRule
import com.gorbkostya.lasttime.domain.model.Category
import com.gorbkostya.lasttime.presentation.list.ListCommand
import com.gorbkostya.lasttime.presentation.list.ListViewModel
import com.gorbkostya.lasttime.presentation.list.previewToday
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel() = ListViewModel(FakeTaskRepository())

    @Test
    fun `при старте база заполняется примерами`() {
        val vm = createViewModel()
        assertEquals(7, vm.state.value.tasks.size)
    }

    @Test
    fun `выбор категории фильтрует список`() {
        val vm = createViewModel()
        vm.onCommand(ListCommand.SelectCategory(Category.HOME))

        val visible = vm.state.value.visible
        assertTrue(visible.isNotEmpty())
        assertTrue(visible.all { it.category == Category.HOME })
    }

    @Test
    fun `сделано обновляет дату`() {
        val vm = createViewModel()
        vm.onCommand(ListCommand.MarkDone(1))
        val taskId = vm.state.value.tasks.first {it.id == 1L}
        assertEquals(taskId.lastDone, previewToday)
    }
}