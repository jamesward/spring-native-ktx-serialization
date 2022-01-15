package kotlinbars.server

import kotlinbars.common.Bar
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.util.*

//@TypeHint(types = [kotlinx.serialization.encoding.CompositeEncoder::class, kotlinx.serialization.descriptors.SerialDescriptor::class])
//@TypeHint(types = [Object::class])
//@TypeHint(types = [Bar::class], access = [TypeAccess.DECLARED_FIELDS, TypeAccess.QUERY_DECLARED_METHODS, TypeAccess.QUERY_PUBLIC_METHODS, TypeAccess.QUERY_DECLARED_CONSTRUCTORS])
//@TypeHint(typeNames = ["kotlinbars.common.Bar\$\$serializer"])
//@TypeHint(typeNames = ["kotlinbars.common.Bar\$Companion"], methods = [MethodHint(name = "serializer", parameterTypes = [])])
@SpringBootApplication
@RestController
class WebApp {

    val bars = Collections.synchronizedList(mutableListOf<Bar>())

    @GetMapping("/api/bars")
    fun fetchBars() = run {
        bars
    }

    @PostMapping("/api/bars")
    fun addBar(@RequestBody bar: Bar) = run {
        bars.add(bar.copy(id = bars.size.toLong()))
    }

}

@Configuration(proxyBeanMethods = false)
class JsonConfig {

    @ExperimentalSerializationApi
    @Bean
    fun kotlinSerializationJsonDecoder() = KotlinSerializationJsonDecoder(Json {
        explicitNulls = false
    })

    @Bean
    fun webConfig(decoder: KotlinSerializationJsonDecoder): WebFluxConfigurer {
        return object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                super.configureHttpMessageCodecs(configurer)
                configurer.defaultCodecs().kotlinSerializationJsonDecoder(decoder)
            }
        }
    }

}

fun main(args: Array<String>) {
    runApplication<WebApp>(*args)
}