/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.api.events;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import net.runelite.api.MenuAction;
import net.runelite.api.Tile;
import online.paescape.Client;

import java.util.function.Consumer;

/**
 * An event when a new entry is added to a right-click menu.
 */

@Data
public class MenuEntryAdded
{
	// Here for RuneLite compatibility (different parameter order)
	public MenuEntryAdded() {

	}
	@Getter
	Tile selectedTile;
	public MenuEntryAdded(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}
	public MenuEntryAdded(String option, String target, MenuAction type, int idx, int actionParam0, int actionParam1)
	{
		this(null,option, target, idx, type, actionParam0, actionParam1, false);
	}



	public MenuEntryAdded(Client client, String option, String target, int idx, MenuAction type, int param0, int param1, boolean forceLeftClick)
	{
		this.client = client;
		this.option = option;
		this.target = target;
		this.idx = idx;
		this.type = type;
		this.actionParam0 = param0;
		this.actionParam1 = param1;
		this.forceLeftClick = forceLeftClick;
		this.menuEntryAdded = this;
	}

	MenuEntryAdded menuEntryAdded;

	public MenuEntryAdded(Client client, String option, MenuAction type, int idx) {
		this(client,option,"",idx,type,-1,-1,false);
	}

	public MenuEntryAdded(String option) {
		this.option = option;
	}

	public MenuEntryAdded setOption(String option) {
		this.option = option;
		return menuEntryAdded;
	}

	public MenuEntryAdded setTarget(String target) {
		this.target = target;
		return menuEntryAdded;
	}

	public MenuEntryAdded setType(MenuAction type) {
		this.type = type;
		return this;
	}

	public MenuEntryAdded createEntry(int idx) {
		this.idx = idx;
		return menuEntryAdded;
	}

	public MenuEntryAdded setActionParam0(int actionParam0) {
		this.actionParam0 = actionParam0;
		return menuEntryAdded;
	}

	public MenuEntryAdded setActionParam1(int actionParam1) {
		this.actionParam1 = actionParam1;
		return menuEntryAdded;
	}

	public MenuEntryAdded setForceLeftClick(boolean forceLeftClick) {
		this.forceLeftClick = forceLeftClick;
		return menuEntryAdded;
	}

	public MenuEntryAdded onClick(Consumer<MenuEntryAdded> consumer) {
		return this;
	}

	public MenuEntryAdded setParent(MenuEntryAdded menuColor) {
		this.menuEntryAdded = menuColor;
		return menuEntryAdded;
	}

	@Getter
	private Client client;

	@Getter
	private String option;

	@Getter
	private String target;

	@Getter
	private MenuAction type;

	public void setIdx(int idx) {
		this.idx = idx;
	}

	@Getter
	private int idx;

	@Getter
	private int actionParam0;

	@Getter
	private int actionParam1;

	@Getter
	private boolean forceLeftClick;
}
