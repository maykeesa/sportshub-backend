package br.com.sporthub.service

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UtilsService {

    companion object {
        fun dataStringToClass(data: String, hora: String): LocalDateTime {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse("$data $hora", formato)
        }

        fun dataStringToClass(dataHora: String?): LocalDateTime {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(dataHora, formato)
        }
    }

}