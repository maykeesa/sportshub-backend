package br.com.sporthub.service

import org.modelmapper.ModelMapper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class UtilsService {

    companion object {
        fun getGenericModelMapper(): ModelMapper{
            val modelMapper = ModelMapper()
            modelMapper.configuration.setAmbiguityIgnored(true)
            modelMapper.configuration.isSkipNullEnabled = true

            return modelMapper
        }

        fun dataHoraStringToLocalDateTime(data: String, hora: String): LocalDateTime {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse("$data $hora", formato)
        }

        fun dataHoraStringToLocalDateTime(dataHora: String): LocalDateTime {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(dataHora, formato)
        }

        fun dataStringToLocalDate(data: String): LocalDate {
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(data, formato)

        }

        fun timeStringToLocalTime(time: String): LocalTime {
            val formato = DateTimeFormatter.ofPattern("HH:mm:ss")
            return LocalTime.parse(time, formato)
        }
    }

}