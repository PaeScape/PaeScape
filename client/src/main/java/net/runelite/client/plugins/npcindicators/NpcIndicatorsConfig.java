/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.npcindicators;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("npcindicators")
public interface NpcIndicatorsConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "drawNpcName",
		name = "Draw npc name",
		description = "Configures whether or not own name should be drawn"
	)
	default boolean drawNpcName()
	{
		return false;
	}
	@ConfigItem(
			position = 1,
			keyName = "NpcNameColor",
			name = "Npc Name color",
			description = "Configures the color of Npc Name"

	)
	default Color NpcNameColor()
	{
		return Color.green;
	}

	@ConfigItem(
		position = 2,
		keyName = "drawNpcTiles",
		name = "Draw tiles",
		description = "Configures whether or not tiles under npc should be drawn"
	)
	default boolean drawTiles()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = "NpcTileColor",
			name = "Npc Tile color",
			description = "Configures the color of Npc Tile"

	)
	default Color NpcTileColor()
	{
		return Color.CYAN;
	}
	@Alpha
	@ConfigItem(
			position = 4,
			keyName = "NpcTileFillColor",
			name = "Fill color",
			description = "Configures the fill color of Npc tile"
	)
	default Color NpcTileFillColor()
	{
		return new Color(0, 0, 0, 50);
	}

	@ConfigItem(
			position = 5,
			keyName = "drawPoly",
			name = "Draw Polygons",
			description = "Configures whether or not polygons should be drawn"
	)
	default boolean drawPoly()
	{
		return false;
	}

	@ConfigItem(
			position = 6,
			keyName = "PolyColor",
			name = "Npc Polygon color",
			description = "Configures the color of Polygon"

	)

	default Color NpcPolyColor()
	{
		return Color.green;
	}
	@ConfigItem(
			position = 7,
			keyName = "npcAttackOption",
			name = "Attackable Only",
			description = "Draw only attackable npcs"

	)

	default boolean drawAttackableOnly()
	{
		return false;
	}

}
