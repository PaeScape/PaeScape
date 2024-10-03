package net.runelite.client.inactiveplugins.camerasmoothing;

import net.runelite.client.config.*;

@ConfigGroup("camerasmoothing"
)
public interface CameraSmoothingConfig extends Config
{
	@Units(
			"%"
	)
	@Range(
			max = 99
	)
	@ConfigItem(
		keyName = "smoothingSpeed",
		name = "Smoothness",
		description = "How slowly the camera moves (0-100%)"
	)
	default int smoothness()
	{
		return 60;
	}

	@ConfigItem(
		keyName="smoothZoom",
		name = "Smooth Zoom",
		description = "Whether or not to smooth the camera zoom"
	)
	default boolean smoothZoom() { return true; }

	@ConfigItem(
			keyName="smoothRotation",
			name = "Smooth Rotation",
			description = "Whether or not to smooth the camera rotation"
	)
	default boolean smoothRotation() { return true; }


}
