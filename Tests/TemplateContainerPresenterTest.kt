package com.appfortype.appfortype.presentation.view.movableView.template_container

import android.net.Uri
import android.util.SparseArray
import com.appfortype.appfortype.data.api.model.ContentSubviews
import com.appfortype.appfortype.data.api.model.EditTemplateMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TemplateContainerPresenterTest {

    @Mock
    lateinit var presenter: TemplateContainerPresenter
    @Mock
    private lateinit var viewState: TemplateContainerMvpView

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter.attachView(viewState)
    }

    @Test
    fun setTemplate() {
        val mockData = mock(EditTemplateMapper::class.java)
        val ac = ArgumentCaptor.forClass(EditTemplateMapper::class.java)
        presenter.template = mockData
        verify(presenter).template = ac.capture()
        Assert.assertEquals(mockData, ac.value)
    }

    @Test
    fun setPhotoList() {
        val listViews = spy(ArrayList<ContentSubviews>()).apply {
            add(ArgumentMatchers.notNull())
        }
        val mockedMap = SparseArray<Uri>()

        listViews.takeIf { listViews.isNotEmpty() }
                ?.let {
                    viewState.setPlaceholderImageList(mockedMap)
                }

        verify(viewState).setPlaceholderImageList(mockedMap)
    }

}