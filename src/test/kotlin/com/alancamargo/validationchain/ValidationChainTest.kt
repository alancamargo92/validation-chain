package com.alancamargo.validationchain

import com.alancamargo.validationchain.model.Validation
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ValidationChainTest {

    @Test
    fun shouldFail0OutOf3Validations() {
        var failCount = 0

        val a = Validation(successCondition = true, onFailureAction = { failCount++ })
        val b = Validation(successCondition = true, onFailureAction = { failCount++ })
        val c = Validation(successCondition = true, onFailureAction = { failCount++ })

        ValidationChain().add(a)
                .add(b)
                .add(c)
                .run()

        assertEquals(failCount, 0)
    }

    @Test
    fun shouldFail2OutOf3Validations() {
        var failCount = 0

        val a = Validation(successCondition = false, onFailureAction = { failCount++ })
        val b = Validation(successCondition = true, onFailureAction = { failCount++ })
        val c = Validation(successCondition = false, onFailureAction = { failCount++ })

        ValidationChain().add(a)
                .add(b)
                .add(c)
                .setOnSuccessListener(object: ValidationChain.OnSuccessListener {
                    override fun onSuccess() {
                        fail()
                    }
                }).run()

        assertEquals(failCount, 2)
    }

    @Test
    fun shouldFail3OutOf3Validations() {
        var failCount = 0

        val a = Validation(successCondition = false, onFailureAction = { failCount++ })
        val b = Validation(successCondition = false, onFailureAction = { failCount++ })
        val c = Validation(successCondition = false, onFailureAction = { failCount++ })

        ValidationChain().add(a)
                .add(b)
                .add(c)
                .setOnSuccessListener(object: ValidationChain.OnSuccessListener {
                    override fun onSuccess() {
                        fail()
                    }
                }).run()

        assertEquals(failCount, 3)
    }

    @Test
    fun withInfixFunction_shouldFail0OutOf3Validations() {
        var failCount = 0

        val a = Validation(successCondition = true, onFailureAction = { failCount++ })
        val b = Validation(successCondition = true, onFailureAction = { failCount++ })
        val c = Validation(successCondition = true, onFailureAction = { failCount++ })

        ValidationChain().add(a)
                .add(b)
                .add(c)
                .run {
                    assertEquals(failCount, 0)
                }
    }

}