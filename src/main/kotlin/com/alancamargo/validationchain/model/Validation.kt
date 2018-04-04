package com.alancamargo.validationchain.model

/**
 * A validation to be performed
 *
 * @param successCondition the condition to be validated
 * @param onFailureAction the action to be taken in case of failure
 *
 * @author Alan Camargo
 */
data class Validation(val successCondition: Boolean, val onFailureAction: () -> Unit)
