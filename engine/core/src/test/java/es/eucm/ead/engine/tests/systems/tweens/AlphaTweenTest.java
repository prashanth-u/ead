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
package es.eucm.ead.engine.tests.systems.tweens;

import es.eucm.ead.engine.entities.EngineEntity;
import es.eucm.ead.engine.systems.tweens.tweencreators.AlphaTweenCreator;
import es.eucm.ead.schema.components.tweens.AlphaTween;
import org.junit.Test;

import es.eucm.ead.engine.systems.tweens.tweencreators.TweenCreator;

import static org.junit.Assert.assertTrue;

public class AlphaTweenTest extends TweenTest {

	@Override
	public Class getTweenClass() {
		return AlphaTween.class;
	}

	@Override
	public TweenCreator getTweenCreator() {
		return new AlphaTweenCreator();
	}

	@Test
	public void testAlpha() {
		AlphaTween alphaTween = new AlphaTween();
		alphaTween.setAlpha(0f);
		alphaTween.setDuration(2f);

		EngineEntity engineEntity = addEntityWithTweens(alphaTween);

		gameLoop.update(1);
		assertTrue("Value is " + engineEntity.getGroup().getColor().a
				+ ". Should be 0.5f",
				engineEntity.getGroup().getColor().a == 0.5f);

		gameLoop.update(1);
		assertTrue("Value is " + engineEntity.getGroup().getColor().a
				+ ". Should be 0f", engineEntity.getGroup().getColor().a == 0f);

		gameLoop.update(1);
		assertTrue("Value is " + engineEntity.getGroup().getColor().a
				+ ". Should be 0f", engineEntity.getGroup().getColor().a == 0f);
	}
}
