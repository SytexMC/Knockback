plugins { `java-library` }

java { toolchain.languageVersion.set(JavaLanguageVersion.of(project.ext.get("javaToolchainVersion") as Int)) }

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(project.ext.get("javaToolchainVersion") as Int)
    }

    javadoc { options.encoding = Charsets.UTF_8.name() }
    processResources { filteringCharset = Charsets.UTF_8.name() }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.6-R0.1-SNAPSHOT")
    compileOnly(project(":common"))
}
