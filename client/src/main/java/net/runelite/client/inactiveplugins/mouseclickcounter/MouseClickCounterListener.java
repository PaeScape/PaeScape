/*
 * Copyright (c) 2020, Robert Espinoza
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.inactiveplugins.mouseclickcounter;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.input.MouseAdapter;
import static net.runelite.client.RuneLite.RUNELITE_DIR;

public class MouseClickCounterListener extends MouseAdapter
{
	private int leftClickCounter;
	private int rightClickCounter;
	private int middleClickCounter;
	private int totalClickCounter;
	private final Client client;
	private final File CLICK_TOTAL_DIR =
		new File(RUNELITE_DIR,"mouseClickCounter");
	private final File CLICK_TOTAL_FILE =
		new File(CLICK_TOTAL_DIR, "click_totals.log");
	private final int SAVE_PERIODICITY = 50;
	private final int NUM_CLICK_TYPES = 4;
	private enum FILE_CLICK_TYPE_INDICES {
		TOTAL(0),
		LEFT(1),
		RIGHT(2),
		MIDDLE(3);
		private final int index;
		FILE_CLICK_TYPE_INDICES(final int newIndex)
		{
			index = newIndex;
		}
		public int getValue() { return index; }
	}

	MouseClickCounterListener(Client client) throws FileNotFoundException
	{
		loadMouseClicks();
		this.client = client;
	}

	@Override
	public MouseEvent mousePressed(MouseEvent event)
	{
		if(client.getGameState() == GameState.LOGGED_IN)
		{

			if (SwingUtilities.isLeftMouseButton(event))
			{
				this.leftClickCounter++;
				this.totalClickCounter++;
			}

			else if (SwingUtilities.isRightMouseButton(event))
			{
				this.rightClickCounter++;
				this.totalClickCounter++;
			}

			else if (SwingUtilities.isMiddleMouseButton(event))
			{
				this.middleClickCounter++;
				this.totalClickCounter++;
			}

			if (this.totalClickCounter%SAVE_PERIODICITY == 0)
			{
				try
				{
					saveMouseClicks();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return event;
	}

	public int getLeftClickCounter() { return this.leftClickCounter; }

	public int getRightClickCounter() { return this.rightClickCounter; }

	public int getMiddleClickCounter() { return this.middleClickCounter; }

	public int getTotalClickCounter() { return this.totalClickCounter; }

	public void resetMouseClickCounterListener()
	{
		this.leftClickCounter = 0;
		this.rightClickCounter = 0;
		this.middleClickCounter = 0;
		this.totalClickCounter = 0;
		try
		{
			saveMouseClicks();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void saveMouseClicks() throws IOException
	{
		if(!this.CLICK_TOTAL_FILE.exists())
		{
			try
			{
				if(!this.CLICK_TOTAL_FILE.createNewFile())
				{
					System.out.println("Failed to create log file");
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		FileWriter writer = new FileWriter(this.CLICK_TOTAL_FILE);

		Integer[] totals = {this.getTotalClickCounter(),
			this.getLeftClickCounter(),
			this.getRightClickCounter(),
			this.getMiddleClickCounter()};

		writer.write(totals[FILE_CLICK_TYPE_INDICES.TOTAL.getValue()] + " ");
		writer.write(totals[FILE_CLICK_TYPE_INDICES.LEFT.getValue()] + " ");
		writer.write(totals[FILE_CLICK_TYPE_INDICES.RIGHT.getValue()] + " ");
		writer.write(totals[FILE_CLICK_TYPE_INDICES.MIDDLE.getValue()] + " ");
		writer.close();
	}

	public void loadMouseClicks() throws FileNotFoundException
	{
		if(!this.CLICK_TOTAL_DIR.mkdir() && this.CLICK_TOTAL_FILE.exists())
		{
			Scanner scanner = new Scanner(this.CLICK_TOTAL_FILE);
			int [] totals = new int[NUM_CLICK_TYPES];
			int ii = 0;
			while(scanner.hasNextInt())
			{
				totals[ii++] = scanner.nextInt();
			}
			if(ii != NUM_CLICK_TYPES)
			{
				resetMouseClickCounterListener();
			}
			else
			{
				this.leftClickCounter =
					totals[FILE_CLICK_TYPE_INDICES.LEFT.getValue()];
				this.rightClickCounter =
					totals[FILE_CLICK_TYPE_INDICES.RIGHT.getValue()];
				this.middleClickCounter =
					totals[FILE_CLICK_TYPE_INDICES.MIDDLE.getValue()];
				this.totalClickCounter =
					totals[FILE_CLICK_TYPE_INDICES.TOTAL.getValue()];
			}
		}
		else
		{
			try
			{
				if(this.CLICK_TOTAL_FILE.createNewFile())
				{
					this.leftClickCounter = 0;
					this.rightClickCounter = 0;
					this.middleClickCounter = 0;
					this.totalClickCounter = 0;
				}
				else
				{
					System.out.println("Failed to create log file");
				}
			}
			catch (IOException e)
			{
				System.out.println("An error occurred creating the log file");
				e.printStackTrace();
			}
		}
	}
}
