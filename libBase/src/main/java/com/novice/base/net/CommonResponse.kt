package com.novice.base.net

import com.novice.base.keep.KeepClass

/**
 * @author novice
 */
class CommonResponse<T> : KeepClass {
    var code: Int = 0
    var msg: String? = null
    var data: T? = null
}