plugins {
    java
    application
    antlr
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-commons:9.4")
    implementation("org.ow2.asm:asm-util:9.4")

    antlr("org.antlr:antlr4:4.11.1")

    testImplementation("org.testng:testng:7.7.0")
}

application {
    mainClass.set("edu.udel.blc.BLCKt")
}

tasks.compileKotlin {
    dependsOn("generateGrammarSource")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-long-messages")
}