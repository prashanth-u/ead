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
package es.eucm.ead.editor.actions;

import es.eucm.ead.editor.control.actions.model.EditScene;
import es.eucm.ead.editor.model.Model;
import es.eucm.ead.schema.editor.components.EditState;
import es.eucm.ead.schema.entities.ModelEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class EditSceneTest extends ActionTest {

	@Test
	public void testEditScene() {
		openEmpty();
		ModelEntity scene1 = new ModelEntity();
		controller.getModel().putResource("scenes/scene1.json", scene1);

		controller.action(EditScene.class, "scenes/scene1.json");
		assertEquals(
				Model.getComponent(controller.getModel().getGame(),
						EditState.class).getEditScene(), "scenes/scene1.json");
		assertSame(controller.getModel().getEditionContext(), scene1);
	}

	@Test
	public void testUnexistingEditScene() {
		openEmpty();
		controller.action(EditScene.class, "ñor");
		assertFalse("ñor".equals(Model.getComponent(
				controller.getModel().getGame(), EditState.class)
				.getEditScene()));
	}
}
