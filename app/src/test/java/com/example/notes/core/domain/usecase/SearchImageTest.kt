package com.example.notes.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.notes.core.domain.reposatory.FakeImageRepo
import com.example.notes.core.presentation.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchImageTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var searchImage: SearchImage
    lateinit var repo:FakeImageRepo

    @Before
    fun setup(){
        repo = FakeImageRepo()
        searchImage = SearchImage(repo)
    }

    @Test
    fun `search images with empty query , return error`()= runTest {
        searchImage.invoke("").collect{
            when(it){
                is Resource.Error -> {
                    assertTrue(it.data?.imageUrl==null)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> Unit
            }
        }

    }

    @Test
    fun `search image with a vaild query but error network , return false`()= runTest {
        repo.shouldReturnError = true
        searchImage.invoke("cars").collect{
            when(it){
                is Resource.Error -> {
                    assertTrue(it.data?.imageUrl==null)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> Unit
            }
        }
    }

    @Test
    fun `search image with a vaild query , return true`()= runTest {

        searchImage.invoke("cars").collect{
            when(it){
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    assertTrue(it.data?.imageUrl!=null)
                }
            }
        }
    }



}