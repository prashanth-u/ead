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

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.Selection;
import es.eucm.ead.editor.control.actions.ModelAction;
import es.eucm.ead.editor.control.commands.CompositeCommand;
import es.eucm.ead.editor.control.commands.FieldCommand;
import es.eucm.ead.editor.control.commands.ListCommand.AddToListCommand;
import es.eucm.ead.editor.model.Q;
import es.eucm.ead.schema.editor.components.Parent;
import es.eucm.ead.schema.entities.ModelEntity;
import es.eucm.ead.schemax.FieldName;

/**
 * <p>
 * Adds a scene element to the current edited scene, and sets it as the
 * {@link Selection#SCENE_ELEMENT} context
 * </p>
 * <dl>
 * <dt><strong>Arguments</strong></dt>
 * <dd><strong>args[0]</strong> <em>SceneElement</em> the scene element to add
 * list</dd>
 * </dl>
 */
public class AddSceneElement extends ModelAction {

	private SetSelection setSelection;

	public AddSceneElement() {
		super(true, false, ModelEntity.class);
	}

	@Override
	public void initialize(Controller controller) {
		super.initialize(controller);
		setSelection = controller.getActions().getAction(SetSelection.class);
	}

	@Override
	public CompositeCommand perform(Object... args) {
		ModelEntity sceneElement = (ModelEntity) args[0];
		Object context = controller.getModel().getSelection()
				.getSingle(Selection.EDITED_GROUP);

		if (context instanceof ModelEntity) {

			ModelEntity root = (ModelEntity) context;

			CompositeCommand compositeCommand = new CompositeCommand();
			compositeCommand.addCommand(new AddToListCommand(root, root
					.getChildren(), sceneElement));

			compositeCommand.addCommand(setSelection.perform(
					Selection.EDITED_GROUP, Selection.SCENE_ELEMENT,
					sceneElement));

			Parent parent = Q.getComponent(sceneElement, Parent.class);
			compositeCommand.addCommand(new FieldCommand(parent,
					FieldName.PARENT, root));

			return compositeCommand;
		} else {
			return null;
		}
	}

}
