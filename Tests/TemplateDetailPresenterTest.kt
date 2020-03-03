package com.appfortype.appfortype.presentation.presenters

import android.app.Activity
import com.appfortype.appfortype.presentation.view_interface.fragment_interface.IDetailTemplateView
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TemplateDetailPresenterTest {

    @Mock
    private lateinit var presenter: TemplateDetailPresenter
    @Mock
    private lateinit var view: IDetailTemplateView

    @Before
    fun setup() {
        presenter.attachView(view)
    }

    @Test
    fun setCurrentPage() {
        val pageNumber = 2
        presenter.currentPage = pageNumber
        verify(presenter).currentPage = pageNumber
    }

    @Test
    fun buySetSuccess() {
        testPurchaseWithResult(true)
    }

    @Test
    fun buySetError() {
        testPurchaseWithResult(false)
    }

    private fun testPurchaseWithResult(isSuccess: Boolean) {

        val mockActivity: Activity = mock(Activity::class.java)
        val mockProductId: String = "productId"
        val mockResId: Int = 123

        `when`(
                presenter.buyProduct(mockActivity, mockProductId)
        ).thenReturn(isSuccess)

        val result = presenter.buyProduct(mockActivity, mockProductId)
        if (!result) view.showErrorDialog(mockResId)

        verify(presenter).buyProduct(mockActivity, mockProductId)

        if (result) {
            verify(view, never()).showErrorDialog(anyInt())
        } else {
            verify(view).showErrorDialog(anyInt())
        }

        assertEquals(result, isSuccess)
    }
}
