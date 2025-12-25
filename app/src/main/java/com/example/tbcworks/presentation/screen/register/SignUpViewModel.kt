package com.example.tbcworks.presentation.screen.register

import com.example.tbcworks.domain.usecase.auth.SendOtpUseCase
import com.example.tbcworks.domain.usecase.auth.SignUpUseCase
import com.example.tbcworks.domain.usecase.auth.VerifyOtpUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateDepartmentSelectedUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateEmailFormatUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateNotEmptyUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordLengthUseCase
import com.example.tbcworks.domain.usecase.validation.ValidatePasswordsMatchUseCase
import com.example.tbcworks.domain.usecase.validation.ValidateTermsAcceptedUseCase
import com.example.tbcworks.domain.validation.ValidationResult
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screen.register.mapper.toDomain
import com.example.tbcworks.presentation.screen.register.model.SignUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val validateNotEmpty: ValidateNotEmptyUseCase,
    private val validateEmailFormat: ValidateEmailFormatUseCase,
    private val validatePasswordLength: ValidatePasswordLengthUseCase,
    private val validatePasswordsMatch: ValidatePasswordsMatchUseCase,
    private val validateTermsAccepted: ValidateTermsAcceptedUseCase,
    private val validateDepartmentSelected: ValidateDepartmentSelectedUseCase
) : BaseViewModel<SignUpContract.SignUpState, SignUpContract.SignUpSideEffect, SignUpContract.SignUpEvent>(
    SignUpContract.SignUpState()
) {

    fun onEvent(event: SignUpContract.SignUpEvent) {
        when (event) {
            is SignUpContract.SignUpEvent.Submit -> validateAndSignUp(event.model)
            is SignUpContract.SignUpEvent.SendOtpClicked -> sendOtp(event.phoneNumber)
            SignUpContract.SignUpEvent.SignInClicked -> sendSideEffect(SignUpContract.SignUpSideEffect.NavigateToSignIn)
        }
    }

    private fun sendOtp(phoneNumber: String) {
        if (validateNotEmpty.validate(phoneNumber) is ValidationResult.Error) {
            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage("Phone number cannot be empty"))
            return
        }

        handleResponse(
            apiCall = { sendOtpUseCase.execute(phoneNumber) },
            onSuccess = { sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage("OTP sent successfully"))
                setState { copy(isLoading = false) }},
            onError = { sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(it))
                setState { copy(isLoading = false) }},
            onLoading = { setState { copy(isLoading = true) } }
        )
    }

    private fun validateAndSignUp(model: SignUpModel) {
        val errors = mutableMapOf<SignUpContract.SignUpState.Field, String>()

        // Validate all fields
        listOf(
            SignUpContract.SignUpState.Field.FIRST_NAME to validateNotEmpty.validate(model.firstName),
            SignUpContract.SignUpState.Field.LAST_NAME to validateNotEmpty.validate(model.lastName),
            SignUpContract.SignUpState.Field.EMAIL to validateEmailFormat.validate(model.email),
            SignUpContract.SignUpState.Field.PHONE to validateNotEmpty.validate(model.phoneNumber),
            SignUpContract.SignUpState.Field.OTP to validateNotEmpty.validate(model.otpCode),
            SignUpContract.SignUpState.Field.DEPARTMENT to validateDepartmentSelected.validate(model.department),
            SignUpContract.SignUpState.Field.PASSWORD to validatePasswordLength.validate(model.password),
            SignUpContract.SignUpState.Field.CONFIRM_PASSWORD to validatePasswordsMatch.validate(model.password, model.confirmPassword),
            SignUpContract.SignUpState.Field.POLICY to validateTermsAccepted.validate(model.isPolicyAccepted)
        ).forEach { (field, result) ->
            if (result is ValidationResult.Error) errors[field] = result.message
        }

        if (errors.isNotEmpty()) {
            setState { copy(errors = errors) }
            errors.values.firstOrNull()?.let { sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(it)) }
            return
        }

        // All validations passed → Verify OTP first
        setState { copy(isLoading = true) }

        handleResponse(
            apiCall = { verifyOtpUseCase.execute(model.phoneNumber, model.otpCode) },
            onSuccess = { responseText ->
                // responseText is a String from your repo
                if (responseText.contains("successfully", ignoreCase = true)) {
                    // OTP verified → proceed to signup
                    handleResponse(
                        apiCall = { signUpUseCase.invoke(model.toDomain()) },
                        onSuccess = {
                            sendSideEffect(SignUpContract.SignUpSideEffect.NavigateToSignIn)
                            setState { copy(isLoading = false) }
                        },
                        onError = {
                            sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(it))
                            setState { copy(isLoading = false) }
                        },
                        onLoading = { setState { copy(isLoading = true) } }
                    )
                } else {
                    sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage("OTP verification failed"))
                    setState { copy(isLoading = false) }
                }
            },
            onError = {
                sendSideEffect(SignUpContract.SignUpSideEffect.ShowMessage(it))
                setState { copy(isLoading = false) }
            },
            onLoading = { setState { copy(isLoading = true) } }
        )

    }
}
