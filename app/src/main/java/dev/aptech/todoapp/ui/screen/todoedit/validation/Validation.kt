package dev.aptech.todoapp.ui.screen.todoedit.validation

sealed class Validation
data object ValidationOk: Validation()
sealed class ValidationFailed: Validation()
data object ValidationEmpty: ValidationFailed()