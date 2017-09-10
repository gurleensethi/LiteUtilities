package com.thetechnocafe.gurleensethi.liteutils

import android.widget.EditText

/**
 * Created by gurleensethi on 08/09/17.
 * Add easy validations on edit text
 *
 * Motivation : Easily validate the text from the edit text based on different parameters
 */

public fun EditText.validator(): Validator = Validator(text.toString())

/*
* Class to process all the filters provided by the user
* */
class Validator(val text: String) {

    /*
    * Boolean to determine whether all the validations has passed successfully.
    * If any validation fails the state is changed to false.
    * Final result is returned to the user
    * */
    private var isValidated = true

    /*
    * If validation fails then this callback is invoked to notify the user about
    * and error
    * */
    private var errorCallback: ((ValidationError?) -> Unit)? = null

    /*
    * If validation is passes then this callback is invoked to notify the user
    * for the same
    * */
    private var successCallback: (() -> Unit)? = null

    /*
    * User settable limits for the numbers of characters that the string can contain
    * */
    private var MINIMUM_CHARACTERS = 0
    private var MAXIMUM_CHARACTERS = Int.MAX_VALUE

    private var VALIDATION_ERROR_TYPE: ValidationError? = null

    public fun validate(): Boolean {
        //Check if the string characters count is in limits
        if (text.length < MINIMUM_CHARACTERS) {
            isValidated = false
            setErrorType(ValidationError.MINIMUM_CHARACTERS)
        } else if (text.length > MAXIMUM_CHARACTERS) {
            isValidated = false
            setErrorType(ValidationError.MAXIMUM_CHARACTERS)
        }

        //Invoke the error callback if supplied by the user
        if (isValidated) {
            successCallback?.invoke()
        } else {
            errorCallback?.invoke(VALIDATION_ERROR_TYPE)
        }

        return isValidated
    }

    public fun email(): Validator {
        if (!text.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))) {
            isValidated = false
            setErrorType(ValidationError.EMAIL)
        }
        return this
    }

    public fun noNumbers(): Validator {
        if (text.matches(Regex(".*\\d.*"))) {
            isValidated = false
            setErrorType(ValidationError.NO_NUMBERS)
        }
        return this
    }

    public fun nonEmpty(): Validator {
        if (text.isEmpty()) {
            isValidated = false
            setErrorType(ValidationError.NON_EMPTY)
        }
        return this
    }

    public fun onlyNumbers(): Validator {
        if (!text.matches(Regex("\\d+"))) {
            isValidated = false
            setErrorType(ValidationError.ONLY_NUMBERS)
        }
        return this;
    }

    public fun allUpperCase(): Validator {
        if (text.toUpperCase() != text) {
            isValidated = false
            setErrorType(ValidationError.ALL_UPPER_CASE)
        }
        return this
    }

    public fun allLowerCase(): Validator {
        if (text.toLowerCase() != text) {
            isValidated = false
            setErrorType(ValidationError.ALL_LOWER_CASE)
        }
        return this
    }

    public fun atLeastOneLowerCase(): Validator {
        if (text.matches(Regex("[A-Z0-9]+"))) {
            isValidated = false
            setErrorType(ValidationError.AT_LEAST_ONE_LOWER_CASE)
        }
        return this
    }

    public fun atLeastOneUpperCase(): Validator {
        if (text.matches(Regex("[a-z0-9]+"))) {
            isValidated = false
            setErrorType(ValidationError.AT_LEAST_ONE_UPPER_CASE)
        }
        return this
    }

    public fun maximumCharacters(length: Int): Validator {
        MAXIMUM_CHARACTERS = length
        return this
    }

    public fun minimumCharacters(length: Int): Validator {
        MINIMUM_CHARACTERS = length
        return this
    }

    public fun addErrorCallback(callback: (ValidationError?) -> Unit): Validator {
        errorCallback = callback
        return this
    }

    public fun addSuccessCallback(callback: () -> Unit): Validator {
        successCallback = callback
        return this
    }

    public fun atLeastOneNumber(): Validator {
        if (!text.matches(Regex(".*\\d.*"))) {
            isValidated = false
            setErrorType(ValidationError.AT_LEAST_ONE_NUMBER)
        }
        return this
    }

    public fun startsWithNonNumber(): Validator {
        if (Character.isDigit(text[0])) {
            isValidated = false
            setErrorType(ValidationError.STARTS_WITH_NON_NUMBER)
        }
        return this
    }

    public fun noSpecialCharacter(): Validator {
        if (!text.matches(Regex("[A-Za-z0-9]+"))) {
            isValidated = false
            setErrorType(ValidationError.NO_SPECIAL_CHARACTER)
        }
        return this
    }

    public fun atLeastOneSpecialCharacter(): Validator {
        if (text.matches(Regex("[A-Za-z0-9]+"))) {
            isValidated = false
            setErrorType(ValidationError.AT_LEAST_ONE_SPECIAL_CHARACTER)
        }
        return this
    }

    public fun containsString(string: String): Validator {
        if (!text.contains(string)) {
            isValidated = false
            setErrorType(ValidationError.CONTAINS_STRING)
        }
        return this
    }

    public fun doesNotContainsString(string: String): Validator {
        if (text.contains(string)) {
            isValidated = false
            setErrorType(ValidationError.DOES_NOT_CONTAINS_STRING)
        }
        return this
    }

    private fun setErrorType(validationError: ValidationError) {
        if (VALIDATION_ERROR_TYPE == null) {
            VALIDATION_ERROR_TYPE = validationError
        }
    }
}

enum class ValidationError {
    MINIMUM_CHARACTERS,
    MAXIMUM_CHARACTERS,
    AT_LEAST_ONE_UPPER_CASE,
    AT_LEAST_ONE_LOWER_CASE,
    ALL_LOWER_CASE,
    ALL_UPPER_CASE,
    ONLY_NUMBERS,
    NON_EMPTY,
    NO_NUMBERS,
    EMAIL,
    AT_LEAST_ONE_NUMBER,
    STARTS_WITH_NON_NUMBER,
    NO_SPECIAL_CHARACTER,
    AT_LEAST_ONE_SPECIAL_CHARACTER,
    CONTAINS_STRING,
    DOES_NOT_CONTAINS_STRING
}