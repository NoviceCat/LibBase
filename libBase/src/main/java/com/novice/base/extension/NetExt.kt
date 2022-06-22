package com.novice.base.extension

import com.novice.base.net.CommonResponse
import com.novice.base.net.HttpNetCode

/**
 * @author novice
 */
fun <T> CommonResponse<T>.process(
    success: (msg: String?, d: T?) -> Unit,
    failed: (status: Int, msg: String?, d: T?) -> Unit
) {
    try {
        if (HttpNetCode.SUCCESS == code) {
            success(msg, data)
        } else {
            failed(code, msg, data)
        }
    } catch (e: Throwable) {
        failed(HttpNetCode.DATA_ERROR, e.message, null)
    }
}



