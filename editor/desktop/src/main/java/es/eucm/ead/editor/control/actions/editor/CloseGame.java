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
package es.eucm.ead.editor.control.actions.editor;

import es.eucm.ead.editor.control.Controller;
import es.eucm.ead.editor.control.Preferences;
import es.eucm.ead.editor.control.actions.EditorAction;
import es.eucm.ead.editor.control.views.NoProjectView;
import es.eucm.ead.editor.model.Model.ModelListener;
import es.eucm.ead.editor.model.events.LoadEvent;
import es.eucm.ead.editor.model.events.LoadEvent.Type;

/**
 * Close the current edited game. It receives no arguments.
 */
public class CloseGame extends EditorAction implements ModelListener<LoadEvent> {

	@Override
	public void initialize(Controller controller) {
		super.initialize(controller);
		controller.getModel().addLoadListener(this);
		setEnabled(controller.getModel().getGame() != null);
	}

	@Override
	public void perform(Object... args) {
		controller.action(ChangeView.class, NoProjectView.class);
		controller.getCommands().clear();
		controller.getModel().reset();
		controller.getModel().notify(
				new LoadEvent(Type.UNLOADED, controller.getModel()));
		controller.getPreferences().putString(Preferences.LAST_OPENED_GAME, "");
	}

	@Override
	public void modelChanged(LoadEvent event) {
		setEnabled(event.getType() == Type.LOADED);
	}
}
