package com.example.nokbackend.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class PrettySqlFormatter : MessageFormattingStrategy {

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = this.javaClass.name
    }

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String {
        return "\n[$category] | $elapsed ms | ${formatSql(category, sql)}"
    }

    private fun stackTrace(): String {
        return Throwable().stackTrace.filter { t ->
            t.toString().startsWith("nokbackend") && !t.toString().contains(this.javaClass.name)
        }.toString()
    }

    private fun formatSql(category: String?, sql: String?): String? {
        return if (!sql.isNullOrBlank() && Category.STATEMENT.name.equals(category)) {
            val trim = sql.trim().lowercase()
            return stackTrace() +
                    if (trim.startsWith("create") || trim.startsWith("alter") || trim.startsWith("comment")) {
                        FormatStyle.HIGHLIGHT.formatter.format(FormatStyle.DDL.formatter.format(sql))
                    } else {
                        FormatStyle.HIGHLIGHT.formatter.format(FormatStyle.BASIC.formatter.format(sql))
                    }
        } else sql
    }
}
