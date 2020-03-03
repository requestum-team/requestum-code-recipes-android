package com.appfortype.appfortype

import com.appfortype.appfortype.data.database.firebaseModel.UserFbModel
import com.appfortype.appfortype.domain.controller.FireBaseCloudHelper
import com.appfortype.appfortype.domain.intefraces.OnResultListener
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mock
import org.mockito.ArgumentMatchers


@RunWith(MockitoJUnitRunner::class)
class FireBaseCloudHelperTest {

    @Mock
    private lateinit var listener: OnResultListener<UserFbModel>
    @Mock
    private lateinit var fireBaseCloudHelper: FireBaseCloudHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun writeDataTest() {
        val mockCount: Int = 2
        val mockId: String = "id"
        val mockData = spy(UserFbModel(mockCount))

        val acInt = ArgumentCaptor.forClass(Int::class.java)
        val acString = ArgumentCaptor.forClass(String::class.java)

        fireBaseCloudHelper.writeData(mockCount, mockId)
        verify(fireBaseCloudHelper).writeData(acInt.capture(), acString.capture())

        assertEquals(mockCount, mockData.count)
        assertEquals(mockId, acString.value)
        assertEquals(mockCount, acInt.value)
    }

    @Test
    fun readDataTest() {
        val mockData = mock(UserFbModel::class.java)
        val acListener = ArgumentCaptor.forClass(OnResultListener::class.java )
                as ArgumentCaptor<OnResultListener<UserFbModel>>
        val acResult = ArgumentCaptor.forClass(UserFbModel::class.java )

        // call fun successfully
        fireBaseCloudHelper.readData("deviceId", listener)
        verify(fireBaseCloudHelper).readData(ArgumentMatchers.anyString(), acListener.capture())
        assertEquals(acListener.value, listener)
        acListener.value.onSuccess(mockData)

        // verify callback calls
        verify(listener, times(1)).onSuccess(acResult.capture())
        verify(listener, never()).onError(anyString())

        // check result from callback is as expected
        assertEquals(acResult.value, mockData)
    }
}