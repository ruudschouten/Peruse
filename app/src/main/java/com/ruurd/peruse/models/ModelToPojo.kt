package com.ruurd.peruse.models

interface ModelToPojo<T> {
    fun toPojo(): T
}