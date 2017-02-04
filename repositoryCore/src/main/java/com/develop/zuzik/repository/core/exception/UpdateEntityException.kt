package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
//TODO: pass String? and handle it here
class UpdateEntityException(message: String) : Exception(message) {
    constructor() : this("UpdateEntityException")
}