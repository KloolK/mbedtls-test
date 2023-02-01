/*
 *  Copyright (c) 2023, Arm Limited, All Rights Reserved
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  This file is part of Mbed TLS (https://www.trustedfirmware.org/projects/mbed-tls/)
 */

package org.mbed.tls.jenkins

import java.util.concurrent.atomic.AtomicLong

import com.cloudbees.groovy.cps.NonCPS

class JobTimestamps {
    final String group, name
    private final AtomicLong start, end, innerStart, innerEnd

    JobTimestamps(String group, String name) {
        this.group      = group
        this.name       = name
        this.start      = new AtomicLong(-1)
        this.end        = new AtomicLong(-1)
        this.innerStart = new AtomicLong(-1)
        this.innerEnd   = new AtomicLong(-1)
    }

    long getStart() {
        return start.get()
    }

    long getEnd() {
        return end.get()
    }

    long getInnerStart() {
        return innerStart.get()
    }

    long getInnerEnd() {
        return innerEnd.get()
    }

    String getCSV() {
        return "$group;$name;$start;$end;$innerStart;$innerEnd"
    }

    private static void set(String name, AtomicLong var, long val) {
        if (!var.compareAndSet(-1, val)) {
            throw new IllegalAccessError("$name set twice")
        }
    }

    void setStart(long val) {
        set('start', start, val)
    }

    void setEnd(long val) {
        set('end', end, val)
    }

    void setInnerStart(long val) {
        set('innerStart', innerStart, val)
    }

    void setInnerEnd(long val) {
        set('innerEnd', innerEnd, val)
    }

    @NonCPS
    @Override
    String toString() {
        return "JobTimestamps(group:$group, name:$name start:${this.@start}, end:${this.@end}, innerStart:${this.@innerStart}, innerEnd:${this.@innerEnd})"
    }
}
