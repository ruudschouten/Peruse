package com.ruurd.peruse.data.pojo

interface PojoToModel<T> {
    fun toModel(): T
}