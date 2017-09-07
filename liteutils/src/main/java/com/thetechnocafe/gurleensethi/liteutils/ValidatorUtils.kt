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

    private var isValidated = true

    public fun validate(): Boolean = isValidated

    public fun noNumbers(): Validator {
        if (text.matches(Regex(".*\\d.*"))) {
            isValidated = false
        }
        return this
    }

    public fun nonEmpty(): Validator {
        if (text.isEmpty()) {
            isValidated = false
        }
        return this
    }

    public fun onlyNumbers(): Validator {
        if (!text.matches(Regex("\\d+"))) {
            isValidated = false
        }
        return this;
    }

    public fun allUpperCase(): Validator {
        if (text.toUpperCase() != text) {
            isValidated = false
        }
        return this
    }

    public fun allLowerCase(): Validator {
        if (text.toLowerCase() != text) {
            isValidated = false
        }
        return this
    }

    public fun atLeastOneLowerCase(): Validator {
        if (text.matches(Regex("[A-Z0-9]+"))) {
            isValidated = false
        }
        return this
    }

    public fun atLeastOneUpperCase(): Validator {
        if (text.matches(Regex("[a-z0-9]+"))) {
            isValidated = false
        }
        return this
    }
}