package online.paescape.util;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class HardwareInformation {

    public static String getGeneratedID() {
        try {
            return getComputerIdentifier();
        } catch (Throwable t) {
            t.printStackTrace();
            // In the event of an error/exception, send a properly formed identifier. Server should check the last field
            // error condition. Last field (getLogicalProcessorCount) should be less than some sane limit, say 128.
            String unknownHash = String.format("%08x", oshi.util.Constants.UNKNOWN.hashCode());
            return unknownHash + "-" + unknownHash + "-" + unknownHash + "-" + unknownHash + "-" + unknownHash;
        }
    }

    public static String getComputerIdentifier() {
        SystemInfo systemInfo = new SystemInfo();
        String vendor = oshi.util.Constants.UNKNOWN;
        try {
            OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
            vendor = operatingSystem.getManufacturer();
        } catch (Throwable t) {
            System.out.println("Running on android");
            if (Objects.equals(System.getProperty("os.name"), "Linux")) {
                vendor = "android";
            }
        }
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();

        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String cpuid = oshi.util.Constants.UNKNOWN;
        int processors = 0;
        String processorIdentifier = oshi.util.Constants.UNKNOWN;
        try {
            CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
            processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
            cpuid = centralProcessor.getProcessorIdentifier().getProcessorID();
            processors = centralProcessor.getLogicalProcessorCount();
        } catch (Throwable t) {
            try {
                cpuid = Files.lines(Paths.get("/proc/cpuinfo"))
                        .filter(line -> line.startsWith("CPU implementer"))
                        .map(line -> line.replaceAll(".*: ", ""))
                        .findFirst().orElse(oshi.util.Constants.UNKNOWN);
                processorIdentifier = Files.lines(Paths.get("/proc/cpuinfo"))
                        .filter(line -> line.startsWith("CPU part"))
                        .map(line -> line.replaceAll(".*: ", ""))
                        .findFirst().orElse(oshi.util.Constants.UNKNOWN);
                processors = Runtime.getRuntime().availableProcessors();
            } catch (IOException ignored) {
            }
        }

        String delimiter = "-";

        return String.format("%08x", vendor.hashCode()) + delimiter
                + String.format("%08x", cpuid.hashCode()) + delimiter
                + String.format("%08x", computerSystem.toString().hashCode()) + delimiter
                + String.format("%08x", processorIdentifier.hashCode()) + delimiter + processors;
    }

}
