import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class BootstrapPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val bootstrapDependencies by configurations.creating {
            isCanBeConsumed = false
            isCanBeResolved = true
            isTransitive = false
        }

        tasks.register<BootstrapTask>("bootstrapDevelop", "develop")
        tasks.register<BootstrapTask>("bootstrapMain", "main")

        tasks.withType<BootstrapTask> {
            this.group = "paescape"
            dependsOn(bootstrapDependencies)
            dependsOn(tasks["distJar"])

            this.clientJar.fileProvider(provider {
                tasks["distJar"].outputs.files.filter { it.name.contains("PaeScape") }.first()
            })


            doLast {
                copy {
                    from(clientJar)
                    from()
                    into("${layout.buildDirectory.asFile.get()}/bootstrap/${type}/")
                }
            }
        }
    }
}
