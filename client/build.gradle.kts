import proguard.gradle.ProGuardTask

group = "online.paescape"
version = "0.0.1"

plugins {
    application
}

project.extra["rootPath"] = rootDir.toString().replace("\\", "/")

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://raw.githubusercontent.com/jbx5/hosting/master")
        maven(url = "https://raw.githubusercontent.com/jbx5/devious-hosting/master")
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.3.0")
    }
}

java {
    setSourceCompatibility(JavaVersion.VERSION_11.toString())
    setTargetCompatibility(JavaVersion.VERSION_11.toString())
}

application {
    project.setProperty("mainClassName", "net.runelite.client.RuneLite")
}

dependencies {

    implementation("com.github.oshi:oshi-core:6.4.10")
    implementation("org.slf4j:slf4j-api:1.7.25")
//    implementation("com.google.guava:guava:32.0-jre")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("commons-io:commons-io:2.7")
    implementation("org.apache.commons:commons-lang3:3.9")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("com.dorkbox:Notify:3.7")
    implementation("com.google.code.gson:gson:2.8.9")

    // https://github.com/aschoerk/reflections8
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-core:2.8.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.8.6")

    implementation(group = "com.google.inject", name = "guice", version = "5.0.1")

    // https://mvnrepository.com/artifact/me.tongfei/progressbar
    implementation("me.tongfei:progressbar:0.10.0")
    implementation("org.jetbrains:annotations:24.0.0")

    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.20")

    val lombok = "org.projectlombok:lombok:1.18.26"
    compileOnly(lombok)
    annotationProcessor(lombok)
    testCompileOnly(lombok)
    testAnnotationProcessor(lombok)

    val lombokVersion = "1.18.24"
    val slf4jVersion = "2.0.12"
    val lwjglVersion = "3.3.1"
    val lwjglClassifiers = arrayOf(
        "natives-linux",
        "natives-windows-x86", "natives-windows",
        "natives-macos", "natives-macos-arm64"
    )
    val joglVersion = "2.4.0-rc-20220318"
    val joglClassifiers = arrayOf(
        "natives-linux-amd64",
        "natives-windows-amd64", "natives-windows-i586",
        "natives-macosx-universal"
    )

    annotationProcessor(group = "org.projectlombok", name = "lombok", version = lombokVersion)

    compileOnly(group = "javax.annotation", name = "javax.annotation-api", version = "1.3.2")
    compileOnly(group = "org.projectlombok", name = "lombok", version = lombokVersion)

    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.4.12")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.8.9")
    implementation(group = "com.google.guava", name = "guava", version = "32.0.0-jre") {
        exclude(group = "com.google.code.findbugs", module = "jsr305")
        exclude(group = "com.google.errorprone", module = "error_prone_annotations")
        exclude(group = "com.google.j2objc", module = "j2objc-annotations")
        exclude(group = "org.codehaus.mojo", module = "animal-sniffer-annotations")
    }
    implementation(group = "com.google.inject", name = "guice", version = "5.0.1")
    implementation(group = "com.google.protobuf", name = "protobuf-javalite", version = "3.21.7")
    implementation(group = "com.jakewharton.rxrelay3", name = "rxrelay", version = "3.0.1")
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.12.0")
    implementation(group = "io.reactivex.rxjava3", name = "rxjava", version = "3.1.2")
    implementation(group = "org.jgroups", name = "jgroups", version = "5.2.2.Final")
    implementation(group = "net.java.dev.jna", name = "jna", version = "5.9.0")
    implementation(group = "net.java.dev.jna", name = "jna-platform", version = "5.9.0")
    implementation(group = "net.sf.jopt-simple", name = "jopt-simple", version = "5.0.4")
    implementation(group = "org.madlonkay", name = "desktopsupport", version = "0.6.0")
    implementation(group = "org.apache.commons", name = "commons-text", version = "1.10.0")
    implementation(group = "org.apache.commons", name = "commons-csv", version = "1.9.0")
    implementation(group = "commons-io", name = "commons-io", version = "2.8.0")
    implementation(group = "org.jetbrains", name = "annotations", version = "22.0.0")
    implementation(group = "com.github.zafarkhaja", name = "java-semver", version = "0.9.0")
    implementation(group = "org.slf4j", name = "slf4j-api", version = slf4jVersion)
    implementation("com.beust:klaxon:5.5")
    // implementation(group = "com.google.archivepatcher", name = "archive-patch-applier", version= "1.0.4")

    implementation(group = "org.lwjgl", name = "lwjgl", version = lwjglVersion)
    implementation(group = "org.lwjgl", name = "lwjgl-opengl", version = lwjglVersion)
    for (classifier in lwjglClassifiers) {
        implementation(group = "org.lwjgl", name = "lwjgl", version = lwjglVersion, classifier = classifier)
        implementation(group = "org.lwjgl", name = "lwjgl-opengl", version = lwjglVersion, classifier = classifier)
    }

    compileOnly(group = "net.runelite", name = "orange-extensions", version = "1.0")
    implementation(group = "net.runelite", name = "discord", version = "1.4")
    implementation(group = "net.runelite.pushingpixels", name = "substance", version = "8.0.02")
    implementation(group = "net.runelite.gluegen", name = "gluegen-rt", version = joglVersion)
    implementation(group = "net.runelite.jogl", name = "jogl-rl", version = joglVersion)
    implementation(group = "net.runelite.jogl", name = "jogl-gldesktop-dbg", version = joglVersion)
    implementation(group = "net.runelite.jocl", name = "jocl", version = "1.0")
    implementation(group = "net.runelite", name = "rlawt", version = "1.3")
    runtimeOnly(group = "net.runelite.pushingpixels", name = "trident", version = "1.5.00")
    for (classifier in joglClassifiers) {
        runtimeOnly(group = "net.runelite.jogl", name = "jogl-rl", version = joglVersion, classifier = classifier)
        runtimeOnly(group = "net.runelite.gluegen", name = "gluegen-rt", version = joglVersion, classifier = classifier)
    }
    runtimeOnly(group = "net.runelite.jocl", name = "jocl", version = "1.0", classifier = "macos-x64")
    runtimeOnly(group = "net.runelite.jocl", name = "jocl", version = "1.0", classifier = "macos-arm64")

    testAnnotationProcessor(group = "org.projectlombok", name = "lombok", version = lombokVersion)
    testCompileOnly(group = "org.projectlombok", name = "lombok", version = lombokVersion)
}

tasks.withType<JavaCompile>().configureEach {
    options.isWarnings = false
    options.isDeprecation = false
    options.isIncremental = true
}

tasks {

    register<JavaExec>("Run-Normal") {
        group = "PaeScape"
        description = "Run PaeScape Vanilla in Normal Mode"
        classpath = project.sourceSets.main.get().runtimeClasspath
        mainClass.set("net.runelite.client.RuneLite")
    }

    register<JavaExec>("Run-Development") {
        group = "PaeScape"
        description = "Run Runelite in Development Mode"
        enableAssertions = true
        args = listOf("--developer-mode")
        classpath = project.sourceSets.main.get().runtimeClasspath
        mainClass.set("net.runelite.client.RuneLite")
    }

    register<JavaExec>("Run-Local") {
        group = "PaeScape"
        description = "Run Runelite in Local Development Mode"
        enableAssertions = true
        args = listOf("--developer-mode", "--server=127.0.0.1")
        classpath = project.sourceSets.main.get().runtimeClasspath
        mainClass.set("net.runelite.client.RuneLite")
    }

    register<JavaExec>("Run-Dev-Server") {
        group = "PaeScape"
        description = "Run Runelite in Local Development Mode"
        enableAssertions = true
        args = listOf("--developer-mode", "--server=142.93.73.247")
        classpath = project.sourceSets.main.get().runtimeClasspath
        mainClass.set("net.runelite.client.RuneLite")
    }

    val depJars = configurations.runtimeClasspath.get().files.filter { !it.name.matches(Regex("client*.jar")) }

    register<Jar>("distJar") {
        dependsOn("proguard")
        group = "build"

        from(depJars.map { zipTree(it) })
        from(zipTree("${layout.buildDirectory.asFile.get()}/${project.name}-${project.version}-obf.jar"))

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "net.runelite.client.RuneLite"
        }

        archiveBaseName.set("PaeScape")
        destinationDirectory.set(layout.buildDirectory.get().asFile)
    }

    register<Jar>("distJarUnobf") {
        dependsOn(jar)
        group = "build"

        from(depJars.map { zipTree(it) })
        from(zipTree("${layout.buildDirectory.asFile.get()}/${project.name}.jar"))

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "net.runelite.client.RuneLite"
        }

        archiveBaseName.set("PaeScape-unobf")
        destinationDirectory.set(layout.buildDirectory.get().asFile)
    }

    jar {
        from(sourceSets.main.get().output)
        destinationDirectory.set(layout.buildDirectory.get().asFile)
    }

    register<Copy>("copyToLibs") {
        dependsOn(compileJava)
        from(depJars)
        into(layout.buildDirectory.file("libs/").get().asFile)
    }

    register<ProGuardTask>("proguard") {
        // this task can't be run from a clean build
        // run 'jar', then run proguard
        group = "build"
        verbose()

        // Use the jar task output as a input jar. This will automatically add the necessary task dependency.
        injars(jar)

        dontwarn("java.lang.invoke.**")

        adaptresourcefilenames("**.png,**.properties,**.xml,**.json")

        keep("class net.runelite.** { *; }")

        keep(
            """
            public class net.runelite.client.RuneLite {
                public static void main(java.lang.String[]);
            }
        """.trimIndent()
        )

        keepattributes("*Annotation*")
        keepattributes("*Signature*")

        outjars("${layout.buildDirectory.asFile.get()}/${project.name}-${project.version}-obf.jar")

        val runtimeAndCompileTimeJars = depJars.toMutableList()

        runtimeAndCompileTimeJars.addAll(configurations.compileClasspath.get().files)

        libraryjars(runtimeAndCompileTimeJars.toSet())

        val javaHome = System.getProperty("java.home")
        // Automatically handle the Java version of this build.
        if (System.getProperty("java.version").startsWith("1.")) {
            // Before Java 9, the runtime classes were packaged in a single jar file.
            libraryjars("$javaHome/lib/rt.jar")
        } else {
            // As of Java 9, the runtime classes are packaged in modular jmod files.
            fileTree(File("$javaHome/jmods")).forEach {
                libraryjars(
                    // filters must be specified first, as a map
                    mapOf(
                        "jarfilter" to "!**.jar",
                        "filter" to "!module-info.class"
                    ),
                    it.absolutePath
                )
            }
        }

        dump("${layout.buildDirectory.asFile.get()}/proguard.dump")
        printconfiguration("${layout.buildDirectory.asFile.get()}/proguard_running.conf")
        printmapping("${layout.buildDirectory.asFile.get()}/proguard_mapping.txt")
        printseeds("${layout.buildDirectory.asFile.get()}/proguard_seeds.txt")
    }
}

apply<BootstrapPlugin>()
