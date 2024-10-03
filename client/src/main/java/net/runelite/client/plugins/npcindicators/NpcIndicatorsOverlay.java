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

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;

@Slf4j
public class NpcIndicatorsOverlay extends Overlay
{
	private static final Color CYAN = new Color(0, 184, 212);
	private static final Color GREEN = new Color(0, 200, 83);
	private static final Color PURPLE = new Color(170, 0, 255);
	private final Client client;
	private final NpcIndicatorsConfig config;
	private final NpcIndicatorsPlugin plugin;

	@Inject
	NpcIndicatorsOverlay(Client client, NpcIndicatorsConfig config, NpcIndicatorsPlugin plugin)
	{
		this.config = config;
		this.client = client;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (NPC npc : client.getNpcs())
		{
			if (npc == null || npc.getName() == null)
			{
				continue;
			}
			if(config.drawAttackableOnly() && !hasAttackOption(npc)){
				continue;
			}
			if (config.drawTiles())
			{
				renderNpcOverlay(graphics, npc, config.NpcTileColor(),config.NpcTileFillColor());
			}
			if (config.drawNpcName())
			{
				final String name = npc.getName().replace('\u00A0', ' ');
				net.runelite.api.Point textLocation = npc.getCanvasTextLocation(graphics,
						name, npc.getModelHeight() + 40);

				if (textLocation != null)
				{
					OverlayUtil.renderTextLocation(graphics, textLocation, name, config.NpcNameColor());
				}
			}
			if (config.drawPoly()){
				renderNpcPolygon(graphics,npc,config.NpcPolyColor());
			}
		}

		return null;
	}

	private boolean hasAttackOption(NPC npc) {
		for(String options : npc.getComposition().getActions()){
			if(options != null && options.contains("Attack"))
				return true;
		}
		return false;
	}

	private void renderNpcPolygon(Graphics2D graphics,NPC npc, Color npcPolyColor) {
		Polygon objectClickbox = npc.getConvexHullPoly();
		if (objectClickbox != null && config.drawPoly())
		{
			graphics.setColor(npcPolyColor);
			graphics.setStroke(new BasicStroke(2));
			graphics.draw(objectClickbox);
			graphics.setColor(new Color(npcPolyColor.getRed(), npcPolyColor.getGreen(), npcPolyColor.getBlue(), 20));
			graphics.fill(objectClickbox);
		}
	}

	private void renderNpcOverlay(Graphics2D graphics, NPC actor, Color npcTileColor, Color npcTileFillColor)
	{
		Polygon poly = actor.getCanvasTilePoly();
		if (poly != null)
		{
			OverlayUtil.renderPolygon(graphics, poly, npcTileColor,npcTileFillColor);
		}
	}
}
