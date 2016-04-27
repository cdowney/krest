package net.cad.models

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class BlogPost(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        @Type(type = "uuid-char")
        var id: UUID? = null,

        var postDateTime: LocalDateTime = LocalDateTime.now(),

        var content: String = ""
)