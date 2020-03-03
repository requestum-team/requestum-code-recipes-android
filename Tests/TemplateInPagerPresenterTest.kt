package com.appfortype.appfortype.presentation.presenters

import android.os.Bundle
import com.appfortype.appfortype.domain.controller.argbuilders.StringArgsBuilder
import com.appfortype.appfortype.domain.intefraces.ITemplateInPagerView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TemplateInPagerPresenterTest {

    @Mock
    private lateinit var presenter: TemplateInPagerPresenter
    @Mock
    private lateinit var viewState: ITemplateInPagerView

    @Before
    fun setup() {
        presenter.attachView(viewState)
    }

    @Test
    fun setParams() {
        val arguments = mock(Bundle::class.java)
        `when`(presenter.setParams(arguments)).thenCallRealMethod()

        presenter.setParams(arguments)
        verify(arguments).getString(StringArgsBuilder.ITEM_ID)?.let { url ->
            viewState.loadBitmap(url, ArgumentMatchers.anyInt())
        }
    }

}