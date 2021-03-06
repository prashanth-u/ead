/**
 * eAdventure is a research project of the
 *    e-UCM research group.
 *
 *    Copyright 2005-2014 e-UCM research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          CL Profesor Jose Garcia Santesmases 9,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.eucm.ead.engine.components.behaviors.events;

/**
 * Created by angel on 11/06/14.
 */
public class RuntimeTimer extends RuntimeBehavior {
	private float time;

	private int repeat;

	private float remainingTime;

	private String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
		this.remainingTime = time;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	/**
	 * Updates the timer
	 * 
	 * @param delta
	 *            time since last update
	 * @return timer's repetition after processing the given delta
	 */
	public int update(float delta) {
		remainingTime -= delta;

		int count = 0;
		if (remainingTime <= 0) {
			count = (int) (Math.floor(Math.abs(remainingTime) / time)) + 1;

			if (repeat > 0) {
				count = Math.min(repeat, count);
				repeat = Math.max(0, repeat - count);
			}

			for (int i = 0; i < count; i++) {
				remainingTime += time;
			}
		}
		return count;
	}

	/**
	 * @return whether timer has finished (no more repetitions awaiting)
	 */
	public boolean isDone() {
		return repeat == 0;
	}
}
