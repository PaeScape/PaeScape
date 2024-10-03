import groovy.json.JsonOutput
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject

open class BootstrapTask @Inject constructor(@Input val type: String) : DefaultTask() {

    @InputFile
    @PathSensitive(PathSensitivity.ABSOLUTE)
    val clientJar: RegularFileProperty = project.objects.fileProperty()

    @Input
    val clientVersion: String = project.version.toString()

    @Input
    val launcherArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "-Xincgc",
        "-XX:+UseConcMarkSweepGC",
        "-XX:+UseParNewGC"
    )

    @Input
    val launcherJvm11Arguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500"
    )

    @Input
    val launcherJvm11WindowsArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Dpaescape.launcher.blacklistedDlls=RTSSHooks.dll,RTSSHooks64.dll,NahimicOSD.dll,k_fps32.dll,k_fps64.dll",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500"
    )

    @Input
    val launcherJvm17Arguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED"
    )

    @Input
    val launcherJvm17MacArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
        "--add-opens=java.desktop/com.apple.eawt=ALL-UNNAMED"
    )

    @Input
    val launcherJvm17WindowsArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Dpaescape.launcher.nojvm=true",
        "-Dpaescape.launcher.blacklistedDlls=RTSSHooks.dll,RTSSHooks64.dll,NahimicOSD.dll,k_fps32.dll,k_fps64.dll",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED"
    )

    @Input
    val clientJvmArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "-Xincgc",
        "-XX:+UseConcMarkSweepGC",
        "-XX:+UseParNewGC",
        "-Dawt.useSystemAAFontSettings=on",
        "-Dswing.aatext=true"
    )

    @Input
    val clientJvm9Arguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "-Dawt.useSystemAAFontSettings=on",
        "-Dswing.aatext=true"
    )

    @Input
    val clientJvm17MacArguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
        "--add-opens=java.desktop/com.apple.eawt=ALL-UNNAMED",
        "-Dawt.useSystemAAFontSettings=on",
        "-Dswing.aatext=true"
    )

    @Input
    val clientJvm17Arguments = arrayOf(
        "-XX:+DisableAttachMechanism",
        "-Xmx1g",
        "-Xss2m",
        "-XX:CompileThreshold=1500",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
        "-Dawt.useSystemAAFontSettings=on",
        "-Dswing.aatext=true"
    )

    @Input
    val dependencyHashes = JsonBuilder()

    private fun hash(file: ByteArray): String {
        return MessageDigest.getInstance("SHA-256").digest(file).fold("", { str, it -> str + "%02x".format(it) })
    }

    private fun getArtifacts(): Array<JsonBuilder> {
        val artifacts = ArrayList<JsonBuilder>()

        val cjar = clientJar.get().asFile
        val sha = hash(cjar.readBytes())
        artifacts.add(
            JsonBuilder(
                "name" to cjar.name,
                "path" to "https://static.paescape.online/${type}/${cjar.name}",
                "size" to cjar.length(),
                "hash" to sha
            )
        )

        dependencyHashes.add(cjar.name to sha)

        return artifacts.toTypedArray()
    }

    @TaskAction
    fun bootstrap() {
        val json = JsonBuilder(
            "projectVersion" to project.version,
            "minimumLauncherVersion" to "1.0.0",
            "launcherArguments" to launcherArguments,
            "launcherJvm11Arguments" to launcherJvm11Arguments,
            "launcherJvm11WindowsArguments" to launcherJvm11WindowsArguments,
            "launcherJvm17Arguments" to launcherJvm17Arguments,
            "launcherJvm17MacArguments" to launcherJvm17MacArguments,
            "launcherJvm17WindowsArguments" to launcherJvm17WindowsArguments,
            "clientJvmArguments" to clientJvmArguments,
            "clientJvm9Arguments" to clientJvm9Arguments,
            "clientJvm17MacArguments" to clientJvm17MacArguments,
            "clientJvm17Arguments" to clientJvm17Arguments,
            "buildCommit" to project.extra["gitCommit"],
            "artifacts" to getArtifacts(),
            "dependencyHashes" to dependencyHashes
        ).toString()

        val prettyJson = JsonOutput.prettyPrint(json)

        val bootstrapDir = File("${project.layout.buildDirectory.asFile.get()}/bootstrap")
        bootstrapDir.mkdirs()

        File(bootstrapDir, "bootstrap-${type}.json").printWriter().use { out ->
            out.println(prettyJson)
        }
    }
}
