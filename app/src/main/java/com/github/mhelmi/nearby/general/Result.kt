package com.github.mhelmi.nearby.general

data class Result<T>(
    val resultType: ResultType,
    val data: T? = null,
    val message: String? = null
) {

    companion object {

        fun <T> error(message: String = ""): Result<T> {
            return Result(ResultType.ERROR, message = message)
        }

        fun <T> success(data: T? = null, message: String = ""): Result<T> {
            return Result(ResultType.SUCCESS, data, message)
        }

        fun <T> connectionError(): Result<T> {
            return Result(ResultType.CONNECTION_ERROR)
        }

        fun <T> emptyDataError(message: String = ""): Result<T> {
            return Result(ResultType.EMPTY_DATA, message = message)
        }

        fun <T> authFailedError(message: String = ""): Result<T> {
            return Result(ResultType.AUTH_FAILED_ERROR, message = message)
        }

        fun <T> validationError(data: T, message: String = ""): Result<T> {
            return Result(ResultType.VALIDATION_ERROR, data, message)
        }
    }
}