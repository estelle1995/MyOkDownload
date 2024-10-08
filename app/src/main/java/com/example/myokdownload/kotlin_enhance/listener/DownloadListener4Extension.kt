/*
 * Copyright (c) 2018 LingoChamp Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myokdownload.kotlin_enhance.listener

import com.example.myokdownload.dowload.DownloadTask
import com.example.myokdownload.dowload.core.breakpoint.BlockInfo
import com.example.myokdownload.dowload.core.breakpoint.BreakpointInfo
import com.example.myokdownload.dowload.core.cause.EndCause
import com.example.myokdownload.dowload.core.listener.DownloadListener4
import com.example.myokdownload.dowload.core.listener.assist.Listener4Assist


typealias onInfoReady = (
    task: DownloadTask,
    info: BreakpointInfo,
    fromBreakpoint: Boolean,
    model: Listener4Assist.Listener4Model
) -> Unit

typealias onProgressBlock = (task: DownloadTask, blockIndex: Int, currentBlockOffset: Long) -> Unit

typealias onProgressWithoutTotalLength = (task: DownloadTask, currentOffset: Long) -> Unit

typealias onBlockEnd = (task: DownloadTask, blockIndex: Int, info: BlockInfo) -> Unit

typealias onTaskEndWithListener4Model = (
    task: DownloadTask,
    cause: EndCause,
    realCause: Exception?,
    model: Listener4Assist.Listener4Model
) -> Unit

/**
 * A concise way to create a [DownloadListener4], only the [DownloadListener4.taskEnd] is necessary.
 */
fun createListener4(
    onTaskStart: onTaskStart? = null,
    onConnectStart: onConnectStart? = null,
    onConnectEnd: onConnectEnd? = null,
    onInfoReady: onInfoReady? = null,
    onProgressBlock: onProgressBlock? = null,
    onProgressWithoutTotalLength: onProgressWithoutTotalLength? = null,
    onBlockEnd: onBlockEnd? = null,
    onTaskEndWithListener4Model: onTaskEndWithListener4Model
): DownloadListener4 = object : DownloadListener4() {
    override fun taskStart(task: DownloadTask) {
        onTaskStart?.invoke(task)
    }

    override fun infoReady(
        task: DownloadTask,
        info: BreakpointInfo,
        fromBreakpoint: Boolean,
        model: Listener4Assist.Listener4Model
    ) {
        onInfoReady?.invoke(task, info, fromBreakpoint, model)
    }

    override fun progressBlock(task: DownloadTask, blockIndex: Int, currentBlockOffset: Long) {
        onProgressBlock?.invoke(task, blockIndex, currentBlockOffset)
    }

    override fun progress(task: DownloadTask, currentOffset: Long) {
        onProgressWithoutTotalLength?.invoke(task, currentOffset)
    }

    override fun blockEnd(task: DownloadTask, blockIndex: Int, info: BlockInfo) {
        onBlockEnd?.invoke(task, blockIndex, info)
    }

    override fun taskEnd(
        task: DownloadTask,
        cause: EndCause,
        realCause: java.lang.Exception?,
        model: Listener4Assist.Listener4Model
    ) {
        onTaskEndWithListener4Model.invoke(task, cause, realCause, model)
    }

    override fun connectStart(
        task: DownloadTask,
        blockIndex: Int,
        requestHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        onConnectStart?.invoke(task, blockIndex, requestHeaderFields)
    }

    override fun connectEnd(
        task: DownloadTask,
        blockIndex: Int,
        responseCode: Int,
        responseHeaderFields: MutableMap<String, MutableList<String>>
    ) {
        onConnectEnd?.invoke(task, blockIndex, responseCode, responseHeaderFields)
    }
}