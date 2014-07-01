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
package es.eucm.ead.editor.control.actions.model;

import es.eucm.ead.editor.control.actions.ModelAction;
import es.eucm.ead.editor.control.commands.Command;
import es.eucm.ead.editor.control.commands.CompositeCommand;
import es.eucm.ead.editor.control.commands.FieldCommand;
import es.eucm.ead.editor.control.commands.SelectionCommand.SetEditionContextCommand;
import es.eucm.ead.schemax.FieldName;
import es.eucm.ead.editor.model.Model;
import es.eucm.ead.schema.editor.components.EditState;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.entities.ResourceCategory;

/**
 * Changes the edited scene.
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>args[0]</strong> <em>String</em> the identifier of edited scene</dd>
 * </dl>
 */
public class EditScene extends ModelAction {

	public EditScene() {
		super(true, false, String.class);
	}

	@Override
	public boolean validate(Object... args) {
		if (super.validate(args)) {
			String sceneId = (String) args[0];
			return controller.getModel().getResource(sceneId,
					ResourceCategory.SCENE) != null;
		}
		return false;
	}

	@Override
	public Command perform(Object... args) {
		ModelEntity game = controller.getModel().getGame();
		EditState editState = Model.getComponent(game, EditState.class);
		String sceneId = (String) args[0];
		ModelEntity scene = (ModelEntity) controller.getModel().getResource(
				sceneId, ResourceCategory.SCENE);

		CompositeCommand commands = new CompositeCommand();
		commands.addCommand(new FieldCommand(editState, FieldName.EDIT_SCENE,
				args[0], true));
		commands.addCommand(new SetEditionContextCommand(controller.getModel(),
				scene));
		return commands;
	}
}
