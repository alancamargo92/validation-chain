package com.alancamargo.validationchain

import com.alancamargo.validationchain.model.Validation

/**
 * A chain of validations
 *
 * The validation chain is only successful if all validations pass
 *
 * @author Alan Camargo
 */
class ValidationChain {

    private val validations = mutableListOf<Validation>()

    private var onSuccessListener: OnSuccessListener? = null

    /**
     * Adds a validation to the chain
     *
     * @param validation the validation
     * @return the chain with the new validation added
     */
    fun add(validation: Validation): ValidationChain {
        validations.add(validation)
        return this
    }

    /**
     * Sets a callback to be triggered if all validations pass
     *
     * @param onSuccessListener the callback
     */
    fun setOnSuccessListener(onSuccessListener: OnSuccessListener): ValidationChain {
        this.onSuccessListener = onSuccessListener
        return this
    }

    /**
     * Runs all validations in the chain.
     * If none of them fail, then the success callback,
     * if any, will be triggered
     */
    fun run() {
        var isValid = true

        for (validation in validations) {
            if (!validation.successCondition) {
                validation.onFailureAction()
                isValid = false
            }
        }

        if (isValid) onSuccessListener?.onSuccess()
    }

    /**
     * Runs all validations in the chain.
     * If none of them fail, then the success action
     * will be triggered
     *
     * @param onSuccessAction the action to be triggered
     *                        in case of success
     */
    infix fun run(onSuccessAction: () -> Unit) {
        var isValid = true

        for (validation in validations) {
            if (!validation.successCondition) {
                validation.onFailureAction()
                isValid = false
            }
        }

        if (isValid) onSuccessAction()
    }

    interface OnSuccessListener {
        /**
         * Function triggered when a validation
         * is successful
         */
        fun onSuccess()
    }

}