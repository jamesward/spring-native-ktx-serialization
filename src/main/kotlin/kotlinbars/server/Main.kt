package kotlinbars.server

import kotlinbars.common.Bar
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.config.WebFluxConfigurer

interface BarRepo : CoroutineCrudRepository<Bar, Long>

//@TypeHint(types = [kotlinx.serialization.encoding.CompositeEncoder::class, kotlinx.serialization.descriptors.SerialDescriptor::class])
//@TypeHint(types = [Object::class])
//@TypeHint(types = [Bar::class], access = [TypeAccess.DECLARED_FIELDS, TypeAccess.QUERY_DECLARED_METHODS, TypeAccess.QUERY_PUBLIC_METHODS, TypeAccess.QUERY_DECLARED_CONSTRUCTORS])
//@TypeHint(typeNames = ["kotlinbars.common.Bar\$\$serializer"])
//@TypeHint(typeNames = ["kotlinbars.common.Bar\$Companion"], methods = [MethodHint(name = "serializer", parameterTypes = [])])
@SpringBootApplication
@RestController
class WebApp(val barRepo: BarRepo) {

    @GetMapping("/api/bars")
    suspend fun fetchBars() = run {
        barRepo.findAll()
    }

    @PostMapping("/api/bars")
    suspend fun addBar(@RequestBody bar: Bar) = run {
        barRepo.save(bar)
        ResponseEntity<Unit>(HttpStatus.NO_CONTENT)
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