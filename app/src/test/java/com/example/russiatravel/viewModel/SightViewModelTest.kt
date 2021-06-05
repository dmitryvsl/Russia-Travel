package com.example.russiatravel.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.russiatravel.network.DataState
import com.example.russiatravel.network.model.Sight
import com.example.russiatravel.repository.SightRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import com.example.russiatravel.network.model.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.ArgumentMatchers.*
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.mockito.internal.stubbing.answers.Returns

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SightViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var sightRepository: SightRepository

    @Mock
    lateinit var sightObserver: Observer<Sight>

    @Mock
    lateinit var sightsObserver: Observer<List<Sight>>

    @Mock
    lateinit var feedbackObserver: Observer<Feedback>

    @Mock
    lateinit var addFeedbackStateObserver: Observer<FeedbackStatus>

    private lateinit var viewModel: SightViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SightViewModel(sightRepository)
    }

    @Test
    fun `getSightDetail success test`() {
        testCoroutineRule.runBlockingTest {
            val expectedSight = Sight(1, "", "", listOf(""), 1f, 1f, "")

            Mockito
                .`when`(sightRepository.getSight(anyInt()))
                .thenReturn(DataState.Success(expectedSight))

            viewModel.getSightDetail(anyInt())
            viewModel.sight.observeForever(sightObserver)

            Mockito.verify(sightRepository).getSight(anyInt())
            Mockito.verify(sightObserver).onChanged(expectedSight)
        }
    }

    @Test
    fun `getSightDetail error test`() {
        val errorMessage = "some error occurred"
        testCoroutineRule.runBlockingTest {
            Mockito
                .`when`(sightRepository.getSight(anyInt()))
                .thenReturn(DataState.Error(errorMessage))

            viewModel.getSightDetail(anyInt())

            Mockito.verify(sightRepository).getSight(anyInt())
            assertEquals(errorMessage, viewModel.loadError.value)
        }
    }


    @Test
    fun `fetchSights by id success test`() {
        testCoroutineRule.runBlockingTest {

            Mockito
                .`when`(sightRepository.fetchSights(anyInt()))
                .thenReturn(DataState.Success(emptyList()))

            viewModel.fetchSights(anyInt())
            viewModel.sights.observeForever(sightsObserver)

            Mockito.verify(sightRepository).fetchSights(anyInt())
            Mockito.verify(sightsObserver).onChanged(emptyList())
        }
    }

    @Test
    fun `fetchSights by id error test`() {
        val errorMessage = "some error occurred"
        testCoroutineRule.runBlockingTest {
            Mockito
                .`when`(sightRepository.fetchSights(anyInt()))
                .thenReturn(DataState.Error(errorMessage))

            viewModel.fetchSights(anyInt())

            Mockito.verify(sightRepository).fetchSights(anyInt())
            assertEquals(errorMessage, viewModel.loadError.value)
        }
    }

    @Test
    fun `fetchSights by location success test`() {
        testCoroutineRule.runBlockingTest {
            Mockito
                .`when`(sightRepository.fetchNearSights(anyFloat(), anyFloat()))
                .thenReturn(DataState.Success(emptyList()))

            viewModel.fetchSights(anyFloat(), anyFloat())
            viewModel.sights.observeForever(sightsObserver)

            Mockito.verify(sightRepository).fetchNearSights(anyFloat(), anyFloat())
            Mockito.verify(sightsObserver).onChanged(emptyList())
        }
    }

    @Test
    fun `fetchSights by location error success`() {
        val errorMessage = "some error occurred"
        testCoroutineRule.runBlockingTest {
            Mockito
                .`when`(sightRepository.fetchNearSights(anyFloat(), anyFloat()))
                .thenReturn(DataState.Error(errorMessage))

            viewModel.fetchSights(anyFloat(), anyFloat())

            Mockito.verify(sightRepository).fetchNearSights(anyFloat(), anyFloat())
            assertEquals(errorMessage, viewModel.loadError.value)

        }
    }

    @Test
    fun `getFeedbacks success test`() {
        testCoroutineRule.runBlockingTest {
            val expectedResult = Feedback(
                totalCount = 1,
                totalRating = 1,
                feedbacks = emptyList()
            )
            Mockito
                .`when`(sightRepository.getFeedbacks(anyInt()))
                .thenReturn(DataState.Success(expectedResult))

            viewModel.feedback.observeForever(feedbackObserver)
            viewModel.getFeedbacks(anyInt())

            Mockito.verify(sightRepository).getFeedbacks(anyInt())
            Mockito.verify(feedbackObserver).onChanged(expectedResult)
        }
    }

    @Test
    fun `getFeedbacks error test`() {
        val errorMessage = "some error occurred"
        testCoroutineRule.runBlockingTest {
            Mockito
                .`when`(sightRepository.getFeedbacks(anyInt()))
                .thenReturn(DataState.Error(errorMessage))

            viewModel.feedback.observeForever(feedbackObserver)
            viewModel.getFeedbacks(anyInt())

            Mockito.verify(sightRepository).getFeedbacks(anyInt())
            assertEquals(errorMessage, viewModel.loadError.value)
        }
    }

    @Test
    fun `removeSights test`() {
        testCoroutineRule.runBlockingTest {
            val sights = listOf(Sight(1, "", "", listOf(), 1f, 1f, ""))

            Mockito
                .`when`(sightRepository.fetchSights(anyInt()))
                .thenReturn(DataState.Success(sights))

            viewModel.fetchSights(anyInt())
            viewModel.removeSights()
            viewModel.sights.observeForever(sightsObserver)

            Mockito.verify(sightRepository).fetchSights(anyInt())
            Mockito.verify(sightsObserver).onChanged(null)
        }
    }

    @Test
    fun `addFeedback test success`() {
        testCoroutineRule.runBlockingTest {
            val expectedResult = FeedbackStatus.SUCCESS
            Mockito
                .`when`(sightRepository.addFeedback(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(DataState.Success("success"))

            viewModel.addFeedbackState.observeForever(addFeedbackStateObserver)

            viewModel.addFeedback(anyInt(), anyInt(), anyString(), anyString())

            Mockito.verify(sightRepository).addFeedback(anyInt(), anyInt(), anyString(), anyString())
            Mockito.verify(addFeedbackStateObserver).onChanged(expectedResult)
        }
    }

    @Test
    fun `addFeedback test error`() {
        testCoroutineRule.runBlockingTest {
            val expectedResult = FeedbackStatus.ERROR
            Mockito
                .`when`(sightRepository.addFeedback(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(DataState.Error("some error occurred"))

            viewModel.addFeedbackState.observeForever(addFeedbackStateObserver)

            viewModel.addFeedback(anyInt(), anyInt(), anyString(), anyString())

            Mockito.verify(sightRepository).addFeedback(anyInt(), anyInt(), anyString(), anyString())
            Mockito.verify(addFeedbackStateObserver).onChanged(expectedResult)
        }
    }

}

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

}
