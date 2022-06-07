package com.techun.paxcomponents.util

import com.orhanobut.logger.Logger


object Util {

    const val SUCCESS = 1
    const val ERROR = 0
    const val PRINTER = 2
    const val CIERRE_EN_DEMANDA = 5

    //    const val NIT_SUGERIDO = 6
    const val STATUS_ICC_READER = 7
    const val CUSTUMER_SUMMARY = 8
    const val ANULACION_REPORTE = 9
    const val CIERRE = 10


    //AESC 2021-11-29 Funcionalidad agregada para una mejor visualizacion de los logs
    fun logsUtils(msg: String, id: Int = 0) {
        when (id) {
            //debug
            0 -> Logger.d(msg)
            //error
            1 -> Logger.e(msg)
            //warning
            2 -> Logger.w(msg)
            //verbose
            3 -> Logger.v(msg)
            //information
            4 -> Logger.i(msg)
            //What a Terrible Failure
            5 -> Logger.wtf(msg)
            //Json
            6 -> Logger.json(msg)
            //XML
            7 -> Logger.xml(msg)
        }
    }
}
