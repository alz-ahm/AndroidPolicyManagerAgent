package com.motorola.adminservices.util

import java.io.DataOutputStream

/**
 * This class is responsible for running shell commands.
 */
object ShellUtil {

    @Throws(Exception::class)
    fun executecmd(cmds: Array<String>) {
        val p = Runtime.getRuntime().exec("su")
        val os = DataOutputStream(p.outputStream)
        for (tmpCmd in cmds) {
            os.writeBytes(tmpCmd + "\n")
        }
        os.writeBytes("exit\n")
        os.flush()
    }
}
