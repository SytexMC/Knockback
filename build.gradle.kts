allprojects {
    group = "me.sytex"
    version = "3.0.0"

    ext { set("javaToolchainVersion", 21) }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
