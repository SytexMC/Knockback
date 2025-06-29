plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java { toolchain.languageVersion.set(JavaLanguageVersion.of(project.ext.get("javaToolchainVersion") as Int)) }

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(project.ext.get("javaToolchainVersion") as Int)
    }

    javadoc { options.encoding = Charsets.UTF_8.name() }
    processResources { filteringCharset = Charsets.UTF_8.name() }

    build { dependsOn(shadowJar) }

    shadowJar { archiveClassifier.set("") }

    withType<Jar> {
        archiveBaseName.set(rootProject.name)
    }

    runServer {
        minecraftVersion("1.21.5")
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.6-R0.1-SNAPSHOT")
    listOf(":common", ":api:1_21", ":api:1_21_3").forEach { implementation(project(it)) }
}

paper {
    main = "me.sytex.knockback.Knockback"

    apiVersion = "1.21"

    name = "Knockback"
    description = "Restores pre-1.21 blast protection mechanics for end crystals and respawn anchors by maintaining the original 60% knockback reduction cap. "
    version = project.version as String

    authors = listOf("Sytex")
    contributors = listOf()
}